package com.secudoc.auth_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.secudoc.auth_service.dto.LoginRequest;
import com.secudoc.auth_service.dto.RegisterUser;
import com.secudoc.auth_service.entity.UserEntity;
import com.secudoc.auth_service.repository.UserRepository;
import com.secudoc.auth_service.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    // ---------------- REGISTER TEST ----------------

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterUser request = new RegisterUser();
        request.setUsername("chinmay");
        request.setPassword("password123");

        when(userRepository.existsByUserName("chinmay"))
                .thenReturn(false);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPass");

        authService.register(request);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    // ---------------- DUPLICATE USER TEST ----------------

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        RegisterUser request = new RegisterUser();
        request.setUsername("chinmay");

        when(userRepository.existsByUserName("chinmay"))
                .thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> authService.register(request));
    }

    // ---------------- LOGIN TEST ----------------

    @Test
    void shouldLoginAndReturnJwtToken() {

        LoginRequest request = new LoginRequest();
        request.setUsername("chinmay");
        request.setPassword("password123");

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "chinmay",
                        "password123"
                );

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(jwtService.generateToken("chinmay"))
                .thenReturn("fake-jwt-token");

        String token = authService.login(request);

        assertEquals("fake-jwt-token", token);
    }
}

