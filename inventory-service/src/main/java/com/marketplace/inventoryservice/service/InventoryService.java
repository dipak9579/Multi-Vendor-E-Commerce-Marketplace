package com.marketplace.inventoryservice.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.marketplace.inventoryservice.entity.Inventory;
import com.marketplace.inventoryservice.repository.InventoryRepository;


@Service
public class InventoryService {


    private final InventoryRepository inventoryRepo;


    public InventoryService(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }


    public Inventory getInventory(Long productId) {
        return inventoryRepo.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }


    @Transactional
    public void reserveStock(Long productId, int quantity) {
        Inventory inventory = getInventory(productId);


        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }


        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepo.save(inventory);
    }


    @Transactional
    public void releaseStock(Long productId, int quantity) {
        Inventory inventory = getInventory(productId);
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepo.save(inventory);
    }

    @Transactional
    public Inventory createInventory(Long productId, int quantity) {

        // Prevent duplicate inventory
        inventoryRepo.findByProductId(productId)
                .ifPresent(inv -> {
                    throw new RuntimeException("Inventory already exists");
                });

        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setQuantity(quantity);

        return inventoryRepo.save(inventory);
    }

}