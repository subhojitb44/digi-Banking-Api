package org.authentication.services.type.auth;

import lombok.*;
import org.authentication.enums.RoleEnum;

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
public class RegisterRequestDto {
    private String username;
    private String password;
    private String name;
    private String phone;
    private RoleEnum role;
}