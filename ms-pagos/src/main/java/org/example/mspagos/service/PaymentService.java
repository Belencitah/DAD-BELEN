package org.example.mspagos.service;


import org.example.mspagos.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Page<Payment> findAll(Pageable pageable);
    Payment findById(Long id);
    Payment create(Payment payment);
    Payment update(Long id, Payment payment);
    void delete(Long id);
}