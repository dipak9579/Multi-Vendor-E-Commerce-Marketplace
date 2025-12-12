package com.dipak.auth_service.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role; // expected: CUSTOMER | SELLER (we map to Role enum)
}
