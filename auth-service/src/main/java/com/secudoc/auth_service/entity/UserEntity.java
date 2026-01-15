package com.secudoc.auth_service.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class UserEntity {

	public class UserCredential {

	    @Id
	    @GeneratedValue
	    private UUID id;

	    @Column(unique = true, nullable = false)
	    private String username;

	    @Column(nullable = false)
	    private String passwordHash;

	    private boolean enabled = true;
	}

	
}
