package com.secudoc.api_gateway.client;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.secudoc.api_gateway.util.ValidateTokenResponse;

import reactor.core.publisher.Mono;


//Purpose is to call validate API if some non-auth api is hit
@Component
public class AuthClient {

	
    private final WebClient webClient;
    
    public AuthClient(WebClient webClient) {
    	this.webClient = webClient;
    }

    public Mono<ValidateTokenResponse> validateToken(String token) {
        return webClient.post()
                .uri("http://localhost:8081/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(ValidateTokenResponse.class);
    }
}

