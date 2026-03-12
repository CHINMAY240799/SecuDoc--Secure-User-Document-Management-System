package com.secudoc.auth_service.dto;

public class UserRegisteredEvent {
	private Long id;
	private String userName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserRegisteredEvent(Long id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	
	
	
}
