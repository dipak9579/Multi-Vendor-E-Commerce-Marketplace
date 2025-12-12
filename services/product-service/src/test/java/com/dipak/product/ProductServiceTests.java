package com.dipak.product;

import com.dipak.product.entity.Product;
import com.dipak.product.repository.ProductRepository;
import com.dipak.product.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

import java.util.List;

@DataJpaTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})
public class ProductServiceTests {

    @Autowired private ProductRepository repo;

    @Test
    void testCreateAndFind() {
        Product p = Product.builder().title("Test").description("desc").price(9.99).sellerId(1L).build();
        Product saved = repo.save(p);
        List<Product> all = repo.findAll();
        Assertions.assertTrue(all.size()>=1);
    }
}
