package com.marketplace.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Data;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/api/inventory")
    void createInventory(@RequestBody InventoryCreateRequest request);

    @Data
    class InventoryCreateRequest {
        private Long productId;
        private int quantity;
    }
}
