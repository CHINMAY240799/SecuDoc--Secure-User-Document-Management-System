package com.secudoc.api_gateway.util;

import java.util.List;

public class ValidateTokenResponse {

    private boolean valid;
    private String username;
    private List<String> roles;

    public ValidateTokenResponse(boolean valid) {
        this.valid = valid;
    }

    public ValidateTokenResponse(boolean valid, String username, List<String> roles) {
        this.valid = valid;
        this.username = username;
        this.roles = roles;
    }

    public boolean isValid() {
        return valid;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}

