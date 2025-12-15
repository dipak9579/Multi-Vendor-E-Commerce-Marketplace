package com.marketplace.userservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.marketplace.userservice.entity.UserProfile;



public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUsername(String username);
}