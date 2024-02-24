package org.authentication.services.interfaces;

import org.authentication.entities.AppUser;
import org.authentication.services.type.auth.AuthenticationRequestDto;
import org.authentication.services.type.auth.RegisterRequestDto;
import org.authentication.services.type.auth.TokenRequestResponse;

/**
 * Created by subho
 * Date: 1/29/2024
 */
public interface AuthenticationServiceInterface {
    TokenRequestResponse register(RegisterRequestDto request);

    Object authenticate(AuthenticationRequestDto request);

    public AppUser validateToken(String token);

    boolean logout(String token);
}