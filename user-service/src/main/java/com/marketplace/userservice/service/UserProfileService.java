package com.marketplace.userservice.service;

import org.springframework.stereotype.Service;

import com.marketplace.userservice.repository.UserProfileRepository;
import com.marketplace.userservice.entity.UserProfile;


@Service
public class UserProfileService {


    private final UserProfileRepository userRepo;


    public UserProfileService(UserProfileRepository userRepo) {
        this.userRepo = userRepo;
    }


    public UserProfile getProfile(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public UserProfile updateProfile(String username, UserProfile updated) {
        UserProfile user = getProfile(username);
        user.setFullName(updated.getFullName());
        user.setPhone(updated.getPhone());
        return userRepo.save(user);
    }
}