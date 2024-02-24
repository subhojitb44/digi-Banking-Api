package org.authentication.repositories;

import org.authentication.entities.AppUser;
import org.authentication.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by subho
 * Date: 1/29/2024
 */
@Repository
public interface UserRepository extends MongoRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByRole(Role role);
}