package com.secudoc.auth_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.secudoc.auth_service.dto.LoginRequest;
import com.secudoc.auth_service.dto.RegisterUser;
import com.secudoc.auth_service.dto.UserRegisteredEvent;
import com.secudoc.auth_service.dto.ValidateTokenResponse;
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
    private final UserEventProducer eventProducer;
    

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           UserEventProducer eventProducer) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.eventProducer = eventProducer;
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
        UserEntity savedUser = userRepository.save(user);
        
        //
        UserRegisteredEvent event = new UserRegisteredEvent(
                savedUser.getId(),
                savedUser.getUsername()
            );
        eventProducer.publishUserRegistered(event);
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
    
    @Override
    public ValidateTokenResponse validate(String token) {
    	 if (!jwtService.isTokenValid(token)) {
             return new ValidateTokenResponse(false);
         }

         String username = jwtService.extractUsername(token);

         return new ValidateTokenResponse(true, username, List.of("USER"));
    }
}
