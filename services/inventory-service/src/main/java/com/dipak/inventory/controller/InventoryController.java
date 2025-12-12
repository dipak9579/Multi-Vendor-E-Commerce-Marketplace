package com.dipak.inventory.controller;

import com.dipak.inventory.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final StockService svc;

    public InventoryController(StockService svc) {
        this.svc = svc;
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Integer> check(@PathVariable Long productId) {
        return ResponseEntity.ok(svc.checkStock(productId));
    }

    @PostMapping("/set/{productId}")
    public ResponseEntity<String> setInitialStock(
            @PathVariable Long productId,
            @RequestParam int amount
    ) {
        svc.createInitialStock(productId, amount);
        return ResponseEntity.ok("Initial stock created");
    }

    @PostMapping("/increase/{productId}")
    public ResponseEntity<Integer> increase(@PathVariable Long productId, @RequestParam int amount) {
        return ResponseEntity.ok(svc.increaseStock(productId, amount));
    }

    @PostMapping("/reserve/{productId}")
    public ResponseEntity<Integer> reserve(@PathVariable Long productId, @RequestParam int qty) {
        return ResponseEntity.ok(svc.reserveStock(productId, qty));
    }

    @PostMapping("/commit/{productId}")
    public ResponseEntity<Integer> commit(@PathVariable Long productId, @RequestParam int qty) {
        return ResponseEntity.ok(svc.commitStock(productId, qty));
    }

    @PostMapping("/release/{productId}")
    public ResponseEntity<Integer> release(@PathVariable Long productId, @RequestParam int qty) {
        return ResponseEntity.ok(svc.releaseStock(productId, qty));
    }
}
