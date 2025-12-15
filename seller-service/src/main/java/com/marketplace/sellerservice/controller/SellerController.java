package com.marketplace.sellerservice.controller;


import org.springframework.web.bind.annotation.*;


import com.marketplace.sellerservice.entity.Seller;
import com.marketplace.sellerservice.service.SellerService;


@RestController
@RequestMapping("/api/sellers")
public class SellerController {


    private final SellerService service;


    public SellerController(SellerService service) {
        this.service = service;
    }


    // Seller applies for registration
    @PostMapping("/register")
    public Seller registerSeller(
            @RequestHeader("X-USER") String username,
            @RequestBody Seller seller) {
        return service.registerSeller(username, seller);
    }


    // Seller views own profile
    @GetMapping("/me")
    public Seller myProfile(@RequestHeader("X-USER") String username) {
        return service.getSellerProfile(username);
    }


    // ADMIN approves seller
    @PutMapping("/{id}/approve")
    public Seller approveSeller(@PathVariable Long id) {
        return service.approveSeller(id);
    }


    // ADMIN blocks seller
    @PutMapping("/{id}/block")
    public Seller blockSeller(@PathVariable Long id) {
        return service.blockSeller(id);
    }
}