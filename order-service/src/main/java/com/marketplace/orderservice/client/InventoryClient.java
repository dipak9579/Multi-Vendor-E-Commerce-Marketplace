package com.marketplace.orderservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.marketplace.orderservice.dto.InventoryRequest;


@FeignClient(name = "inventory-service")
public interface InventoryClient {


    @PostMapping("/api/inventory/reserve")
    void reserveStock(@RequestBody InventoryRequest request);


    @PostMapping("/api/inventory/release")
    void releaseStock(@RequestBody InventoryRequest request);
}