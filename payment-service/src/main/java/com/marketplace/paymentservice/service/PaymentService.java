package com.marketplace.paymentservice.service;


import org.springframework.stereotype.Service;

import java.util.Random;


import com.marketplace.paymentservice.client.OrderClient;
import com.marketplace.paymentservice.dto.PaymentRequest;
import com.marketplace.paymentservice.entity.*;
import com.marketplace.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final OrderClient orderClient;

    public PaymentService(PaymentRepository paymentRepo,
                          OrderClient orderClient) {
        this.paymentRepo = paymentRepo;
        this.orderClient = orderClient;
    }

    public Payment processPayment(PaymentRequest request) {


        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.INITIATED);

        paymentRepo.saveAndFlush(payment); // 🔥 IMPORTANT

        boolean success = new Random().nextBoolean();

        try {
            if (success) {
                payment.setStatus(PaymentStatus.SUCCESS);
                orderClient.updateOrderStatus(request.getOrderId(), "PAID");
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                orderClient.updateOrderStatus(request.getOrderId(), "PAYMENT_FAILED");
            }
        } catch (Exception e) {
            // ❗ Order service down or error
            payment.setStatus(PaymentStatus.FAILED);
        }

        return paymentRepo.save(payment);
    }

}
