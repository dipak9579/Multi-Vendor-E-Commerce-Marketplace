package com.marketplace.adminservice.controller;


import org.springframework.web.bind.annotation.*;


import com.marketplace.adminservice.dto.AdminMetricsResponse;
import com.marketplace.adminservice.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final AdminService service;


    public AdminController(AdminService service) {
        this.service = service;
    }


    @PutMapping("/sellers/{id}/approve")
    public void approveSeller(@PathVariable Long id) {
        service.approveSeller(id);
    }


    @PutMapping("/sellers/{id}/block")
    public void blockSeller(@PathVariable Long id) {
        service.blockSeller(id);
    }


    @PutMapping("/users/{username}/block")
    public void blockUser(@PathVariable String username) {
        service.blockUser(username);
    }


    @GetMapping("/metrics")
    public AdminMetricsResponse metrics() {
        return service.getMetrics();
    }
}