package org.ewallet.authentication.repositories;


import org.ewallet.authentication.entities.Role;
import org.ewallet.authentication.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(RoleEnum role);
}