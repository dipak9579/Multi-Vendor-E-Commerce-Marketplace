package com.marketplace.userservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

import com.marketplace.userservice.service.UserProfileService;
import com.marketplace.userservice.entity.UserProfile;


@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserProfileService service;


    public UserController(UserProfileService service) {
        this.service = service;
    }


    @GetMapping("/me")
    public UserProfile getProfile(@RequestHeader("X-USER") String username) {
        return service.getProfile(username);
    }


    @PutMapping("/me")
    public UserProfile updateProfile(
            @RequestHeader("X-USER") String username,
            @RequestBody UserProfile profile) {
        return service.updateProfile(username, profile);
    }
}