package com.dipak.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;

    // 1-argument constructor for convenience
    public AuthResponse(String token) {
        this.token = token;
        this.message = "Success";
    }
}
