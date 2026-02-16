package com.secudoc.auth_service.service;

import com.secudoc.auth_service.dto.LoginRequest;
import com.secudoc.auth_service.dto.RegisterUser;
import com.secudoc.auth_service.dto.ValidateTokenResponse;

public interface AuthService {

    void register(RegisterUser request);
    String login(LoginRequest request);
    ValidateTokenResponse validate(String token);
}

