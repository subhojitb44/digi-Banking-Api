package org.ewallet.authentication.services.interfaces;

import org.ewallet.authentication.entities.AppUser;
import org.ewallet.authentication.services.dtos.auth.AuthenticationRequestDto;
import org.ewallet.authentication.services.dtos.auth.TokenRequestResponse;
import org.ewallet.authentication.services.dtos.auth.RegisterRequestDto;

public interface AuthenticationServiceInterface {
    TokenRequestResponse register(RegisterRequestDto request);
    Object authenticate(AuthenticationRequestDto request);
    public AppUser validateToken(String token);
}