package org.example.mspagos.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements org.example.mspagos.service.PaymentService {

    private final org.example.mspagos.repository.PaymentRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<org.example.mspagos.entity.Payment> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public org.example.mspagos.entity.Payment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
    }

    @Override
    public org.example.mspagos.entity.Payment create(org.example.mspagos.entity.Payment payment) {
        basicChecks(payment);
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
        return repository.save(payment);
    }

    @Override
    public org.example.mspagos.entity.Payment update(Long id, org.example.mspagos.entity.Payment data) {
        basicChecks(data);
        org.example.mspagos.entity.Payment current = findById(id);

        current.setSaleId(data.getSaleId());
        current.setPaymentDate(data.getPaymentDate() != null ? data.getPaymentDate() : current.getPaymentDate());
        current.setAmount(data.getAmount());
        current.setMethod(data.getMethod());
        current.setStatus(data.getStatus());
        current.setReference(data.getReference());

        return repository.save(current);
    }

    @Override
    public void delete(Long id) {
        org.example.mspagos.entity.Payment existing = findById(id);
        repository.delete(existing);
    }

    // ===== Helpers (sin jakarta.validation) =====
    private void basicChecks(org.example.mspagos.entity.Payment p) {
        if (p.getSaleId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "saleId is required");
        }
        if (p.getAmount() == null || p.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "amount must be >= 0");
        }
        if (isBlank(p.getMethod())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "method is required");
        }
        if (isBlank(p.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status is required");
        }
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}