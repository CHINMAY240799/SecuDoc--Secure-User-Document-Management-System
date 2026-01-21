package com.secudoc.auth_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_credentials")
public class UserEntity {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long user_id;

	    @Column(unique = true, nullable = false)
	    private String userName;

	    @Column(nullable = false)
	    private String passwordHash;

	    private boolean enabled = true;

		public Long getId() {
			return user_id;
		}

		public void setId(Long user_id) {
			this.user_id = user_id;
		}

		public String getUsername() {
			return userName;
		}

		public void setUsername(String username) {
			this.userName = username;
		}

		public String getPasswordHash() {
			return passwordHash;
		}

		public void setPasswordHash(String passwordHash) {
			this.passwordHash = passwordHash;
		}
	   
	
}
