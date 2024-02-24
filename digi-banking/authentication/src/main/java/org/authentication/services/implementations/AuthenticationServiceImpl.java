package org.authentication.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.authentication.entities.AppUser;
import org.authentication.enums.AvailabilityStateEnum;
import org.authentication.repositories.UserRepository;
import org.authentication.services.interfaces.AuthenticationServiceInterface;
import org.authentication.services.interfaces.UserServiceInterface;
import org.authentication.services.security.JwtServiceImpl;
import org.authentication.services.type.auth.AuthenticationRequestDto;
import org.authentication.services.type.auth.RegisterRequestDto;
import org.authentication.services.type.auth.TokenRequestResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

/**
 * Created by subho
 * Date: 1/29/2024
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationServiceInterface {
    private final UserRepository userRepository;
    private final UserServiceInterface userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenRequestResponse register(RegisterRequestDto request) {
        boolean isUsernameExists = userService.isUsernameAlreadyExists(request.getUsername());

        if (isUsernameExists)
            return null;

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .status(AvailabilityStateEnum.ONLINE)
                .role(Collections.singletonList(request.getRole()))
                .build();

        try {
            user = userService.saveUser(user);
            log.info("A New user was created successfully {}", user);
        } catch (Exception e) {
            return null;
        }

        String jwtToken = jwtServiceImpl.generateToken(user);

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
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "400";
        }

        AppUser user = userService.findUserByUsername(request.getUsername());
        if (user.getStatus().equals(AvailabilityStateEnum.BANNED))
            return "403";

        String jwtToken = jwtServiceImpl.generateToken(user);

        return getAuthResponse(jwtToken);
    }

    private TokenRequestResponse getAuthResponse(String token) {
        return TokenRequestResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AppUser validateToken(String token) {
        final String username = jwtServiceImpl.extractUsername(token);
        AppUser userApp = userService.findUserByUsername(username);

        if (username.equals(userApp.getUsername()) && !jwtServiceImpl.isTokenExpired(token)) {
            jwtServiceImpl.generateToken(userApp);
            return userApp;
        } else {
            return null;
        }
    }

    @Override
    public boolean logout(String token) {
        final String username = jwtServiceImpl.extractUsername(token);
        AppUser userApp = userService.findUserByUsername(username);
        return Objects.nonNull(userApp);
    }

}