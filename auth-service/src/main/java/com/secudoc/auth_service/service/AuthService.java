package com.secudoc.auth_service.service;

import com.secudoc.auth_service.dto.RegisterUser;

public interface AuthService {

    void register(RegisterUser request);
}

