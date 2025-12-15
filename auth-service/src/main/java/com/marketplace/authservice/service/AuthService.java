package com.marketplace.authservice.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marketplace.authservice.repository.UserRepository;
import com.marketplace.authservice.repository.RoleRepository;

import com.marketplace.authservice.entity.User;
import com.marketplace.authservice.entity.Role;

import com.marketplace.authservice.dto.RegisterRequest;
import com.marketplace.authservice.dto.LoginRequest;

import com.marketplace.authservice.security.JwtUtil;



@Service
public class AuthService {


    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;


    public AuthService(UserRepository userRepo, RoleRepository roleRepo,
                       PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }


    public void register(RegisterRequest req) {

        // 1️⃣ Prevent duplicate users
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 2️⃣ Normalize & validate role
        String requestedRole = req.getRole().toUpperCase();

        if (!requestedRole.equals("CUSTOMER") && !requestedRole.equals("SELLER")) {
            throw new RuntimeException("Invalid role");
        }

        // 3️⃣ Fetch role from DB
        Role role = roleRepo.findByName(requestedRole)
                .orElseThrow(() -> new RuntimeException("Role not found: " + requestedRole));

        // 4️⃣ Create user
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        // ✅ ASSIGN ROLE (THIS WAS MISSING)
        user.getRoles().add(role);

        // 5️⃣ Save user
        userRepo.save(user);
    }



    public String login(LoginRequest req) {
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));


        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }


        return jwtUtil.generateToken(user);
    }
}