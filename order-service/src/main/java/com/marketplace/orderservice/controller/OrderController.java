package com.marketplace.orderservice.controller;


import org.springframework.web.bind.annotation.*;
import java.util.List;


import com.marketplace.orderservice.dto.CreateOrderRequest;
import com.marketplace.orderservice.entity.Order;
import com.marketplace.orderservice.service.OrderService;
import com.marketplace.orderservice.dto.OrderResponseDTO;
import com.marketplace.orderservice.dto.OrderItemResponseDTO;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponseDTO placeOrder(
            @RequestHeader("X-USER") String username,
            @RequestBody CreateOrderRequest request) {

        return service.placeOrder(username, request);
    }

    @GetMapping("/me")
    public List<OrderResponseDTO> myOrders(
            @RequestHeader("X-USER") String username) {

        return service.myOrders(username);
    }

    @PutMapping("/{orderId}/status")
    public void updateStatus(@PathVariable Long orderId,
                             @RequestParam String status) {
        service.updateOrderStatus(orderId, status);
    }

}
