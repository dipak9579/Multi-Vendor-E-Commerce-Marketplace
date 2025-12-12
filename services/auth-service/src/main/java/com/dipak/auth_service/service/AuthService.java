package com.dipak.auth_service.service;

import com.dipak.auth_service.dto.AuthResponse;
import com.dipak.auth_service.dto.LoginRequest;
import com.dipak.auth_service.dto.RegisterRequest;
import com.dipak.auth_service.dto.RegisterResponse;
import com.dipak.auth_service.entity.AppUser;
import com.dipak.auth_service.entity.Role;
import com.dipak.auth_service.repository.UserRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtService jwtService, AuthenticationManager authManager) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public RegisterResponse register(RegisterRequest req) {
        if (repo.existsByUsername(req.getUsername()))
            throw new RuntimeException("Username taken");

        Role role = switch (req.getRole().toUpperCase()) {
            case "SELLER" -> Role.ROLE_SELLER;
            case "ADMIN" -> Role.ROLE_ADMIN;
            default -> Role.ROLE_CUSTOMER;
        };

        AppUser user = AppUser.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .role(role)
                .build();

        repo.save(user);

        return new RegisterResponse("User registered successfully");
    }


    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        return new AuthResponse(jwtService.generateToken(req.getUsername()));
    }
}