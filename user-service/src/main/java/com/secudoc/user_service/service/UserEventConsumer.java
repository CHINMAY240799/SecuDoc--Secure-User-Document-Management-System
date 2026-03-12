package com.secudoc.user_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.secudoc.user_service.dto.UserRegisteredEvent;
import com.secudoc.user_service.entity.User;
import com.secudoc.user_service.repository.UserRepository;

@Component
public class UserEventConsumer {

	
    private final UserRepository userRepository;

    public UserEventConsumer(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@KafkaListener(topics = "user-registered-topic", groupId = "user-service-group")
    public void handleUserRegistered(UserRegisteredEvent event) {
        
		System.out.println("Kafka event received: " + event);
		
        User user = new User();

        user.setId(event.getId());
        user.setUsername(event.getUserName());

        userRepository.save(user);
        
        
        
    }
}
