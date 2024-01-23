package org.ewallet.authentication.services.interfaces;

import org.ewallet.authentication.enums.RoleEnum;
import org.ewallet.authentication.entities.AppUser;

import java.util.List;

public interface UserServiceInterface {
    List<AppUser> getAllUsers();
    AppUser saveUser(AppUser user);
    AppUser findUserByUsername(String username);
    AppUser findUserByRole(RoleEnum role);
    boolean isUsernameAlreadyExists(String username);
}