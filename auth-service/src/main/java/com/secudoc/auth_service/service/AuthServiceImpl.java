package com.secudoc.auth_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.secudoc.auth_service.dto.LoginRequest;
import com.secudoc.auth_service.dto.RegisterUser;
import com.secudoc.auth_service.entity.UserEntity;
import com.secudoc.auth_service.repository.UserRepository;
import com.secudoc.auth_service.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {
    
	private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
    
    @Override
    public String login(LoginRequest request) {
      
    	log.info("Login attempt for username: {}", request.getUsername());
    try {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );
        
        return jwtService.generateToken(authentication.getName());
    }
    
    catch (Exception e){
    	
    	log.error("Authentication FAILED for user: {}", request.getUsername());
        log.error("Reason: {}", e.getMessage());

        throw e;
    	
    	
    }

        
    }
}
