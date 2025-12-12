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

        if (req.getUsername() == null || req.getUsername().isBlank())
            throw new IllegalArgumentException("Username is required");

        if (req.getEmail() == null || req.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");

        if (req.getPassword() == null || req.getPassword().isBlank())
            throw new IllegalArgumentException("Password is required");

        if (repo.existsByUsername(req.getUsername()))
            throw new IllegalArgumentException("Username already exists");

        if (repo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already registered");

        Role role;

        try {
            role = switch (req.getRole().toUpperCase()) {
                case "SELLER" -> Role.ROLE_SELLER;
                case "ADMIN" -> Role.ROLE_ADMIN;
                case "CUSTOMER" -> Role.ROLE_CUSTOMER;
                default -> throw new IllegalArgumentException("Invalid role: " + req.getRole());
            };
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid role");
        }

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

        if (req.getUsername() == null || req.getUsername().isBlank())
            throw new IllegalArgumentException("Username required");

        if (req.getPassword() == null || req.getPassword().isBlank())
            throw new IllegalArgumentException("Password required");

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        return new AuthResponse(jwtService.generateToken(req.getUsername()));
    }

    public boolean sellerExists(Long id) {
        return repo.findById(id)
                .filter(user -> user.getRole() == Role.ROLE_SELLER)
                .isPresent();
    }
}