package com.marketplace.adminservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "seller-service")
public interface SellerClient {


    @PutMapping("/api/sellers/{id}/approve")
    void approveSeller(@PathVariable Long id);


    @PutMapping("/api/sellers/{id}/block")
    void blockSeller(@PathVariable Long id);
}