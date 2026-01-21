package com.secudoc.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.secudoc.auth_service.dto.RegisterUser;
import com.secudoc.auth_service.entity.UserEntity;
import com.secudoc.auth_service.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterUser request) {

        // Check duplicate user
        if (userRepository.existsByUserName(request.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        // Map DTO → Entity
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());

        // Encrypt password
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // Save to DB
        userRepository.save(user);
    }
}
