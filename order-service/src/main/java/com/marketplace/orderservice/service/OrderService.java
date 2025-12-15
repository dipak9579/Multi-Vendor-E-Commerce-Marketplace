package com.marketplace.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.marketplace.orderservice.client.InventoryClient;
import com.marketplace.orderservice.client.PaymentClient;
import com.marketplace.orderservice.dto.CreateOrderRequest;
import com.marketplace.orderservice.dto.InventoryRequest;
import com.marketplace.orderservice.dto.PaymentRequest;
import com.marketplace.orderservice.entity.Order;
import com.marketplace.orderservice.entity.OrderItem;
import com.marketplace.orderservice.entity.OrderStatus;
import com.marketplace.orderservice.repository.OrderRepository;
import com.marketplace.orderservice.dto.OrderResponseDTO;
import com.marketplace.orderservice.dto.OrderItemResponseDTO;


@Service
public class OrderService {   // ✅ THIS WAS MISSING

    private final OrderRepository orderRepo;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;

    public OrderService(OrderRepository orderRepo,
                        InventoryClient inventoryClient,
                        PaymentClient paymentClient) {
        this.orderRepo = orderRepo;
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
    }

    @Transactional
    public OrderResponseDTO placeOrder(String username, CreateOrderRequest request) {

        Order order = new Order();
        order.setUsername(username);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = request.getItems().stream().map(i -> {
            OrderItem item = new OrderItem();
            item.setProductId(i.getProductId());
            item.setQuantity(i.getQuantity());
            item.setPrice(i.getPrice());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toCollection(ArrayList::new)); // ✅ MUTABLE


        order.setItems(items);
        order.setTotalAmount(
                items.stream()
                        .mapToDouble(i -> i.getPrice() * i.getQuantity())
                        .sum()
        );

        Order savedOrder = orderRepo.save(order);

        // Reserve inventory
        for (OrderItem item : items) {
            InventoryRequest invReq = new InventoryRequest();
            invReq.setProductId(item.getProductId());
            invReq.setQuantity(item.getQuantity());
            inventoryClient.reserveStock(invReq);
        }

        savedOrder.setStatus(OrderStatus.PAYMENT_PENDING);
        orderRepo.save(savedOrder);

        PaymentRequest payReq = new PaymentRequest();
        payReq.setOrderId(savedOrder.getId());
        payReq.setAmount(savedOrder.getTotalAmount());
        paymentClient.initiatePayment(payReq);

        return mapToDTO(savedOrder);
    }


    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status));
        orderRepo.save(order);
    }



    public List<OrderResponseDTO> myOrders(String username) {
        return orderRepo.findByUsername(username)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


    private OrderResponseDTO mapToDTO(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUsername(order.getUsername());
        dto.setStatus(order.getStatus().name());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());

        dto.setItems(
                order.getItems().stream().map(item -> {
                    OrderItemResponseDTO itemDto = new OrderItemResponseDTO();
                    itemDto.setProductId(item.getProductId());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setPrice(item.getPrice());
                    return itemDto;
                }).toList()
        );

        return dto;
    }

}
