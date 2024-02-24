package org.authentication.config.security;

import lombok.RequiredArgsConstructor;
import org.authentication.services.interfaces.UserServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by subho
 * Date: 1/29/2024
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserServiceInterface userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::findUserByUsername;
    }

    @Bean
    // The AuthenticationProvider interface is used to authenticate a user. It takes an Authentication object as input,
    // which contains the credentials of the user (such as a username and password), and returns an authenticated Authentication object if the credentials are valid.
    public AuthenticationProvider authenticationProvider() {
        //The DaoAuthenticationProvider class is an implementation of AuthenticationProvider that authenticates a user
        // by checking the credentials against a user database. It does this by using a UserDetailsService to load the user data,
        // and a PasswordEncoder to encode and compare the passwords.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}