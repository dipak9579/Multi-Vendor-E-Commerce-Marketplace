package com.dipak.product.repository;
import com.dipak.product.es.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Long> {
    List<ProductDocument> findByTitleContaining(String title);
}
