package com.dipak.product.service;

import com.dipak.product.client.InventoryClient;
import com.dipak.product.client.SellerClient;
import com.dipak.product.dto.ProductRequest;
import com.dipak.product.exception.SellerNotFoundException;
import com.dipak.product.entity.Category;
import com.dipak.product.entity.Product;
import com.dipak.product.repository.CategoryRepository;
import com.dipak.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final CategoryRepository categoryRepo;
    private final SellerClient sellerClient;
    private final InventoryClient inventoryClient;
    private final ProductIndexService indexService;

    public ProductService(ProductRepository repo,
                          CategoryRepository categoryRepo,
                          SellerClient sellerClient,
                          InventoryClient inventoryClient,
                          ProductIndexService indexService) {

        this.repo = repo;
        this.categoryRepo = categoryRepo;
        this.sellerClient = sellerClient;
        this.inventoryClient = inventoryClient;
        this.indexService = indexService;
    }

    // -----------------------------------------------------------
    // LIST ALL
    // -----------------------------------------------------------
    public List<Product> listAll() {
        return repo.findAll();
    }

    // -----------------------------------------------------------
    // GET BY ID
    // -----------------------------------------------------------
    public Optional<Product> get(Long id) {
        return repo.findById(id);
    }

    // -----------------------------------------------------------
    // CREATE PRODUCT (stock removed from product entity)
    // -----------------------------------------------------------
    public Product create(ProductRequest dto, String imagePath) {

        // Validate Category
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Invalid Category ID"));

        // Validate Seller
        if (!sellerClient.existsById(dto.getSellerId())) {
            throw new SellerNotFoundException("Seller not found with ID: " + dto.getSellerId());
        }

        // Create product (NO STOCK FIELD)
        Product product = Product.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .brand(dto.getBrand())
                .category(category)
                .sellerId(dto.getSellerId())
                .imagePath(imagePath)
                .build();

        // Save product to get ID
        Product saved = repo.save(product);

        // ✔ Create initial stock in inventory
        try {
            inventoryClient.createInitialStock(saved.getId(), dto.getInitialStock());
        } catch (Exception ex) {
            System.err.println("⚠ Inventory Service unreachable: " + ex.getMessage());
        }

        indexService.index(saved);
        return saved;
    }

    // -----------------------------------------------------------
    // UPDATE PRODUCT (no stock sync)
    // -----------------------------------------------------------
    public Product update(Long id, ProductRequest dto, String imagePath) {

        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Invalid Category ID"));

        if (!sellerClient.existsById(dto.getSellerId())) {
            throw new SellerNotFoundException("Seller ID " + dto.getSellerId() + " does not exist");
        }

        // Update product fields
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setBrand(dto.getBrand());
        existing.setCategory(category);
        existing.setSellerId(dto.getSellerId());

        if (imagePath != null) {
            existing.setImagePath(imagePath);
        }

        Product saved = repo.save(existing);
        indexService.index(saved);

        return saved;
    }

    // -----------------------------------------------------------
    // DELETE PRODUCT
    // -----------------------------------------------------------
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // -----------------------------------------------------------
    // GET PRODUCTS BY SELLER
    // -----------------------------------------------------------
    public List<Product> bySeller(Long sellerId) {
        return repo.findBySellerId(sellerId);
    }
}
