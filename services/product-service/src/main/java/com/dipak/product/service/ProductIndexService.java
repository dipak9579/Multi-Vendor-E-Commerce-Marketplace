package com.dipak.product.service;

import com.dipak.product.entity.Product;
import com.dipak.product.es.ProductDocument;
import com.dipak.product.repository.ProductDocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductIndexService {
    private final ProductDocumentRepository docRepo;

    public ProductIndexService(ProductDocumentRepository docRepo) { this.docRepo = docRepo; }

    public void index(Product p) {
        ProductDocument doc = ProductDocument.builder()
                .id(p.getId())
                .title(p.getTitle())
                .description(p.getDescription())
                .price(p.getPrice())
                .brand(p.getBrand())
                .category(p.getCategory()!=null ? p.getCategory().getName() : null)
                .averageRating(p.getAverageRating())
                .build();
        docRepo.save(doc);
    }
}
