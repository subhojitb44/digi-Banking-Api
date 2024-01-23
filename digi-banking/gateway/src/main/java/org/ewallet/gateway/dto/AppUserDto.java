package org.ewallet.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class AppUserDto implements Serializable {
    private String  id;
    private String uuid;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String cin;
    private String status;
    private Object role;
}