package com.marketplace.orderservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.marketplace.orderservice.dto.PaymentRequest;


@FeignClient(name = "payment-service")
public interface PaymentClient {


    @PostMapping("/api/payments/initiate")
    void initiatePayment(@RequestBody PaymentRequest request);
}