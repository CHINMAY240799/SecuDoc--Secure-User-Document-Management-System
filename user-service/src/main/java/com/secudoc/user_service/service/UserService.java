package com.secudoc.user_service.service;

import org.springframework.stereotype.Service;

import com.secudoc.user_service.entity.User;
import com.secudoc.user_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
    	this.userRepository = userRepository;
    }
    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User getUserById(String id) {
    	return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    	
    }
    
    public User saveProfile(User user) {
    	return userRepository.save(user);
    }
    
    
}