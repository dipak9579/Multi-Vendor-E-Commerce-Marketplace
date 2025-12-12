package com.dipak.inventory.service;

import com.dipak.inventory.entity.Stock;
import com.dipak.inventory.repository.StockRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository repo;
    private final KafkaTemplate<String, String> kafka;

    public StockService(StockRepository repo, KafkaTemplate<String, String> kafka) {
        this.repo = repo;
        this.kafka = kafka;
    }

    public int checkStock(Long productId) {
        return repo.findByProductId(productId)
                .map(Stock::getAvailable)
                .orElse(0);
    }

    // -------------------- FIXED: NO DUPLICATE STOCK ROWS --------------------
    public Stock createInitialStock(Long productId, int amount) {
        return repo.findByProductId(productId)
                .orElseGet(() -> {
                    Stock stock = Stock.builder()
                            .productId(productId)
                            .available(amount)
                            .reserved(0)
                            .build();
                    return repo.save(stock);
                });
    }

    @Transactional
    public int increaseStock(Long productId, int amount) {
        Stock stock = repo.findByProductId(productId)
                .orElseGet(() -> createInitialStock(productId, 0));

        stock.setAvailable(stock.getAvailable() + amount);
        repo.save(stock);

        kafka.send("INVENTORY_UPDATED", productId + ":" + stock.getAvailable());
        return stock.getAvailable();
    }

    @Transactional
    public int reserveStock(Long productId, int qty) {
        Stock stock = repo.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock record not found"));

        if (stock.getAvailable() < qty)
            throw new RuntimeException("Insufficient stock");

        stock.setAvailable(stock.getAvailable() - qty);
        stock.setReserved(stock.getReserved() + qty);

        repo.save(stock);
        kafka.send("STOCK_RESERVED", productId + ":" + qty);

        return stock.getAvailable();
    }

    @Transactional
    public int commitStock(Long productId, int qty) {
        Stock stock = repo.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock record not found"));

        if (stock.getReserved() < qty)
            throw new RuntimeException("Reserved stock error");

        stock.setReserved(stock.getReserved() - qty);
        repo.save(stock);

        kafka.send("INVENTORY_UPDATED", productId + ":" + stock.getAvailable());
        return stock.getAvailable();
    }

    @Transactional
    public int releaseStock(Long productId, int qty) {
        Stock stock = repo.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock record not found"));

        stock.setReserved(stock.getReserved() - qty);
        stock.setAvailable(stock.getAvailable() + qty);

        repo.save(stock);

        kafka.send("INVENTORY_UPDATED", productId + ":" + stock.getAvailable());
        return stock.getAvailable();
    }
}