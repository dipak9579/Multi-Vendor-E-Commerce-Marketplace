package com.marketplace.authservice.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marketplace.authservice.entity.User;
import com.marketplace.authservice.entity.Role;
import com.marketplace.authservice.repository.UserRepository;
import com.marketplace.authservice.repository.RoleRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(
            UserRepository userRepo,
            RoleRepository roleRepo,
            PasswordEncoder encoder) {

        return args -> {

            // 1️⃣ Create ADMIN role if not exists
            Role adminRole = roleRepo.findByName("ADMIN")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ADMIN");
                        return roleRepo.save(r);
                    });

            // 2️⃣ Create ADMIN user if not exists
            if (!userRepo.existsByUsername("admin")) {

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@marketplace.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.getRoles().add(adminRole);

                userRepo.save(admin);

                System.out.println("✅ Default ADMIN user created");
            }
        };
    }
}
