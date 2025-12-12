package com.dipak.product.controller;

import com.dipak.product.entity.Product;
import com.dipak.product.dto.ProductRequest;
import com.dipak.product.service.ProductService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService svc;
    private final Path uploadDir = Paths.get("uploads");

    public ProductController(ProductService svc) {
        this.svc = svc;
        try { Files.createDirectories(uploadDir); } catch (IOException ignored) {}
    }

    // ----------------- GET ALL -------------------
    @GetMapping
    public List<Product> list() {
        return svc.listAll();
    }

    // ----------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return svc.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ----------------- CREATE (MULTIPART) -------------------
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createMultipart(
            @RequestPart("product") ProductRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        String imagePath = saveFile(image);

        Product saved = svc.create(request, imagePath);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ----------------- CREATE (JSON ONLY) -------------------
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createJson(@RequestBody ProductRequest request) {
        Product saved = svc.create(request, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ----------------- UPDATE (MULTIPART) -------------------
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateMultipart(
            @PathVariable Long id,
            @RequestPart("product") ProductRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        String imagePath = saveFile(image);

        Product updated = svc.update(id, request, imagePath);
        return ResponseEntity.ok(updated);
    }

    // ----------------- UPDATE (JSON ONLY) -------------------
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateJson(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        Product updated = svc.update(id, request, null);
        return ResponseEntity.ok(updated);
    }

    // ----------------- DELETE -------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------- FILE SAVE HELPER -------------------
    private String saveFile(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            return null;
        }

        String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path dest = uploadDir.resolve(filename);
        Files.copy(image.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

        return dest.toString();
    }
}
