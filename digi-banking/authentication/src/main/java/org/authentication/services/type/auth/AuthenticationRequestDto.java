package org.authentication.services.type.auth;

import lombok.*;

/**
 * Created by subho
 * Date: 1/29/2024
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
