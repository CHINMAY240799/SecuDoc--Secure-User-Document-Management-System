package com.secudoc.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.secudoc.api_gateway.client.AuthClient;

import reactor.core.publisher.Mono;

@Component

public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final AuthClient authClient;
    
    public AuthenticationFilter(AuthClient authClient) {
    	
    	this.authClient = authClient;
    	
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        
        // If it is auth api then delegate to the next filter
        if (path.startsWith("/auth/login") || path.startsWith("/auth/register"))
            return chain.filter(exchange);

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);

        return authClient.validateToken(authHeader)
                .flatMap(response -> {

                    if (!response.isValid())
                        return onError(exchange, "Invalid Token", HttpStatus.UNAUTHORIZED);

                    // Inject user headers
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-Username", response.getUsername())
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}