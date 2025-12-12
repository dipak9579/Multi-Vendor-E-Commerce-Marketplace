package com.dipak.product.controller;

import com.dipak.product.es.ProductDocument;
import com.dipak.product.repository.ProductDocumentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final ProductDocumentRepository repo;
    public SearchController(ProductDocumentRepository repo) { this.repo = repo; }

    @GetMapping("/q")
    public List<ProductDocument> search(@RequestParam String q) {
        return repo.findByTitleContaining(q);
    }

    // Add filter endpoints as needed
}
