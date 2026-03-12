package com.secudoc.auth_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.secudoc.auth_service.dto.UserRegisteredEvent;

@Service
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public UserEventProducer(KafkaTemplate <String, Object>kafkaTemplate) {
    	
    	this.kafkaTemplate = kafkaTemplate;
    }

    public void publishUserRegistered(UserRegisteredEvent event) {

        kafkaTemplate.send("user-registered-topic", event);
    }
}
