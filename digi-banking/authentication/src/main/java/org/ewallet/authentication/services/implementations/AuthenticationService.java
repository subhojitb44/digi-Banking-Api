package org.ewallet.authentication.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ewallet.authentication.client.apis.WalletApis;
import org.ewallet.authentication.dto.WalletDto;
import org.ewallet.authentication.enums.AvailabilityStateEnum;
import org.ewallet.authentication.repositories.UserRepository;
import org.ewallet.authentication.services.dtos.auth.AuthenticationRequestDto;
import org.ewallet.authentication.services.dtos.auth.TokenRequestResponse;
import org.ewallet.authentication.services.dtos.auth.RegisterRequestDto;
import org.ewallet.authentication.services.interfaces.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ewallet.authentication.entities.AppUser;
import org.ewallet.authentication.services.interfaces.AuthenticationServiceInterface;
import org.ewallet.authentication.services.interfaces.RoleServiceInterface;
import org.ewallet.authentication.services.security.JwtService;

import java.util.UUID;


@Service @RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements AuthenticationServiceInterface {
    private final UserRepository userRepository;
    private final UserServiceInterface userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleServiceInterface roleService;
    private final WalletApis walletApis;

    @Override
    public TokenRequestResponse register(RegisterRequestDto request) {
        boolean isUsernameExists = userService.isUsernameAlreadyExists(request.getUsername());

        if(isUsernameExists)
            return null;

        AppUser user = AppUser.builder()
                .uuid(UUID.randomUUID().toString())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .status(AvailabilityStateEnum.ONLINE)
                .role(roleService.findRoleByName(request.getRole()))
                .build();

        try {
            user = userService.saveUser(user);
            log.info("A New user was created successfully {}",user);
        }catch (Exception e){
            return null;
        }

        callWalletServiceForRegisteredUserToCreateWalletForHim(user.getUuid());

        String jwtToken = jwtService.generateToken(user);

        return getAuthResponse(jwtToken);
    }

    @Override
    public Object authenticate(AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch(AuthenticationException e){
            e.printStackTrace();
            return "400";
        }

        AppUser user = userService.findUserByUsername(request.getUsername());
        if(user.getStatus().equals(AvailabilityStateEnum.BANNED))
            return "403";

        String jwtToken = jwtService.generateToken(user);

        return getAuthResponse(jwtToken);
    }
    private void callWalletServiceForRegisteredUserToCreateWalletForHim(String userReference){
        try {
           ResponseEntity<Object> walletDto = walletApis.createWalletApi(WalletDto.builder()
                    .ownerReference(userReference)
                    .name("wallet")
                    .balance("0")
                    .build());
           log.info("This is the new wallet that has been created based on this created user {}",walletDto.getBody());
        } catch (Exception e){
            log.info("The wallet was not created , something went wrong");
        }
    }
    private TokenRequestResponse getAuthResponse(String token) {
        return TokenRequestResponse.builder()
                .token(token)
                .build();
    }
    @Override
    public AppUser validateToken(String token) {
        final String username = jwtService.extractUsername(token);
        AppUser userApp = userService.findUserByUsername(username);

        if (username.equals(userApp.getUsername()) && !jwtService.isTokenExpired(token)) {
            jwtService.generateToken(userApp);
            return userApp;
        } else {
            return null;
        }
    }
}