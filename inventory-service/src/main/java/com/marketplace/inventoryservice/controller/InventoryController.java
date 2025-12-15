package com.marketplace.inventoryservice.controller;


import org.springframework.web.bind.annotation.*;


import com.marketplace.inventoryservice.dto.ReserveStockRequest;
import com.marketplace.inventoryservice.dto.CreateInventoryRequest;
import com.marketplace.inventoryservice.service.InventoryService;


@RestController
@RequestMapping("/api/inventory")
public class InventoryController {


    private final InventoryService service;


    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    public void createInventory(@RequestBody CreateInventoryRequest req) {
        service.createInventory(req.getProductId(), req.getQuantity());
    }



    @GetMapping("/{productId}")
    public int checkStock(@PathVariable Long productId) {
        return service.getInventory(productId).getQuantity();
    }


    @PostMapping("/reserve")
    public void reserveStock(@RequestBody ReserveStockRequest req) {
        service.reserveStock(req.getProductId(), req.getQuantity());
    }


    @PostMapping("/release")
    public void releaseStock(@RequestBody ReserveStockRequest req) {
        service.releaseStock(req.getProductId(), req.getQuantity());
    }
}