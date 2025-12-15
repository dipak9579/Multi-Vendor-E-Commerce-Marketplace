package com.marketplace.productservice.controller;


import org.springframework.web.bind.annotation.*;
import java.util.List;


import com.marketplace.productservice.entity.Product;
import com.marketplace.productservice.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService service;


    public ProductController(ProductService service) {
        this.service = service;
    }


    // SELLER adds product
    @PostMapping
    public Product addProduct(
            @RequestHeader("X-USER") String sellerUsername,
            @RequestParam String category,
            @RequestBody Product product) {
        return service.addProduct(sellerUsername, product, category);
    }


    // SELLER updates product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product) {
        return service.updateProduct(id, product);
    }


    // SELLER deletes product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }


    // CUSTOMER searches products
    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return service.searchByKeyword(keyword);
    }


    @GetMapping("/category/{name}")
    public List<Product> byCategory(@PathVariable String name) {
        return service.searchByCategory(name);
    }
}