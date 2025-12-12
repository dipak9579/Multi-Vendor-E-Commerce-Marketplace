package com.dipak.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface SellerClient {

    @GetMapping("/api/auth/exists/seller/{id}")
    boolean existsById(@PathVariable Long id);
}
