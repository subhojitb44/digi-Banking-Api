package org.authentication.services.type.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by subho
 * Date: 1/29/2024
 */
@Builder
@Getter
@Setter
@ToString
public class TokenRequestResponse {
    private String token;
}