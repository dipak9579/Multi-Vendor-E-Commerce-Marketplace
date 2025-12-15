package com.marketplace.adminservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "order-service")
public interface OrderClient {


    @GetMapping("/api/orders/admin/count")
    long totalOrders();
}