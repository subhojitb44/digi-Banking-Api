package org.ewallet.authentication.services.implementations;

import lombok.RequiredArgsConstructor;

import org.ewallet.authentication.enums.RoleEnum;
import org.springframework.stereotype.Service;
import org.ewallet.authentication.entities.Role;
import org.ewallet.authentication.repositories.RoleRepository;
import org.ewallet.authentication.services.interfaces.RoleServiceInterface;

@Service @RequiredArgsConstructor
public class RoleService implements RoleServiceInterface {
    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(RoleEnum roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}