package com.dipak.inventory;

import com.dipak.inventory.entity.Stock;
import com.dipak.inventory.repository.StockRepository;
import com.dipak.inventory.service.StockService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;

@DataJpaTest
public class StockServiceTest {

    @Autowired private StockRepository repo;
    @MockBean private KafkaTemplate<String,String> kafkaTemplate;

    @Test
    void testIncreaseAndCheck() {
        Stock s = Stock.builder().productId(100L).available(10).reserved(0).build();
        repo.save(s);
        StockService svc = new StockService(repo, kafkaTemplate);
        int val = svc.increaseStock(100L, 5);
        Assertions.assertEquals(15, val);
    }
}
