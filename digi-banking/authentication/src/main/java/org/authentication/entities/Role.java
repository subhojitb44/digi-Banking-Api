package org.authentication.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.authentication.enums.RoleEnum;

/**
 * Created by subho
 * Date: 1/29/2024
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Role {
    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }
}
