package com.dipak.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    // -----------------------------
    // CREATE INITIAL STOCK
    // -----------------------------
    @PostMapping("/api/inventory/set/{productId}")
    void createInitialStock(
            @PathVariable Long productId,
            @RequestParam int amount
    );

    // -----------------------------
    // INCREASE STOCK
    // -----------------------------
    @PostMapping("/api/inventory/increase/{productId}")
    Integer increaseStock(
            @PathVariable Long productId,
            @RequestParam int amount
    );

    // -----------------------------
    // CHECK CURRENT AVAILABLE STOCK
    // -----------------------------
    @GetMapping("/api/inventory/check/{productId}")
    Integer checkStock(@PathVariable Long productId);

    // -----------------------------
    // RESERVE STOCK (Order placed)
    // -----------------------------
    @PostMapping("/api/inventory/reserve/{productId}")
    Integer reserveStock(
            @PathVariable Long productId,
            @RequestParam int qty
    );

    // -----------------------------
    // COMMIT STOCK (Payment success)
    // -----------------------------
    @PostMapping("/api/inventory/commit/{productId}")
    Integer commitStock(
            @PathVariable Long productId,
            @RequestParam int qty
    );

    // -----------------------------
    // RELEASE STOCK (Order failed/cancelled)
    // -----------------------------
    @PostMapping("/api/inventory/release/{productId}")
    Integer releaseStock(
            @PathVariable Long productId,
            @RequestParam int qty
    );
}
