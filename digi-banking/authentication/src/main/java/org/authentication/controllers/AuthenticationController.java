package org.authentication.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.authentication.entities.AppUser;
import org.authentication.services.implementations.AuthenticationServiceImpl;
import org.authentication.services.type.auth.AuthenticationRequestDto;
import org.authentication.services.type.auth.RegisterRequestDto;
import org.authentication.services.type.auth.TokenRequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by subho
 * Date: 1/29/2024
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/authenticate")
public class AuthenticationController {
    private final AuthenticationServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDto request) {
        TokenRequestResponse authResponse = service.register(request);
        return (authResponse == null)
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This username is already taken try another one")
                : ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequestDto request) {
        Object authResponse = service.authenticate(request);
        if (authResponse.equals("403"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You can't reach the application for now because the admin has banned you");
        else if (authResponse.equals("400"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Credentials or the account is not even exists");

        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/validate-token")
    public AppUser validateToken(@PathParam("token") String token) {
        log.info("Received token {}", token);
        return service.validateToken(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@PathParam("token") String token) {
        boolean logoutSuccess = service.logout(token);
        if (logoutSuccess) {
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed");
        }
    }
}