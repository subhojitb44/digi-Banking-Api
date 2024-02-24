package org.authentication.services.implementations;

import lombok.RequiredArgsConstructor;
import org.authentication.entities.AppUser;
import org.authentication.repositories.UserRepository;
import org.authentication.services.interfaces.UserServiceInterface;
import org.authentication.services.security.JwtServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by subho
 * Date: 1/29/2024
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean isUsernameAlreadyExists(String username) {
        return findUserByUsername(username) != null;
    }
}