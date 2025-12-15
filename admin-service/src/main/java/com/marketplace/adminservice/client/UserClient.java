package com.marketplace.adminservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "user-service")
public interface UserClient {


    @PutMapping("/api/users/{username}/block")
    void blockUser(@PathVariable String username);
}