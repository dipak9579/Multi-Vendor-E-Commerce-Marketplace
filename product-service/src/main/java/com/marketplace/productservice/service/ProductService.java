package com.marketplace.productservice.service;


import org.springframework.stereotype.Service;
import com.marketplace.productservice.client.InventoryClient;
import java.util.List;


import com.marketplace.productservice.entity.*;
import com.marketplace.productservice.repository.*;


@Service
public class ProductService {


    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final SellerProductRepository sellerProductRepo;
    private final InventoryClient inventoryClient;



    public ProductService(ProductRepository productRepo,
                          CategoryRepository categoryRepo,
                          SellerProductRepository sellerProductRepo,
                          InventoryClient inventoryClient) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.sellerProductRepo = sellerProductRepo;
        this.inventoryClient = inventoryClient;
    }


    public Product addProduct(String sellerUsername, Product product, String categoryName) {

        Category category = categoryRepo.findByName(categoryName)
                .orElseGet(() -> categoryRepo.save(new Category(null, categoryName)));

        product.setCategory(category);
        Product savedProduct = productRepo.save(product);

        SellerProductMapping mapping = new SellerProductMapping();
        mapping.setSellerUsername(sellerUsername);
        mapping.setProduct(savedProduct);
        sellerProductRepo.save(mapping);

        // ✅ AUTO-CREATE INVENTORY
        InventoryClient.InventoryCreateRequest req =
                new InventoryClient.InventoryCreateRequest();
        req.setProductId(savedProduct.getId());
        req.setQuantity(100); // default initial stock

        System.out.println("🔥 Calling Inventory Service... productId = " + savedProduct.getId());

        inventoryClient.createInventory(req);

        System.out.println("✅ Inventory created");


        return savedProduct;
    }



    public Product updateProduct(Long productId, Product updated) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());


        return productRepo.save(product);
    }


    public void deleteProduct(Long productId) {
        productRepo.deleteById(productId);
    }


    public List<Product> searchByKeyword(String keyword) {
        return List.of();
    }

    public List<Product> searchByCategory(String categoryName) {
        return productRepo.findByCategory_Name(categoryName);
    }


}