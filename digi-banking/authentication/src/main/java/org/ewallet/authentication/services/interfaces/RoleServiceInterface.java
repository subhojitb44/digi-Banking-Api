package org.ewallet.authentication.services.interfaces;


import org.ewallet.authentication.enums.RoleEnum;
import org.ewallet.authentication.entities.Role;

public interface RoleServiceInterface {
    Role findRoleByName(RoleEnum roleName);
}