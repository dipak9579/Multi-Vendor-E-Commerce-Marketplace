package com.marketplace.paymentservice.repository;


import com.marketplace.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);

}