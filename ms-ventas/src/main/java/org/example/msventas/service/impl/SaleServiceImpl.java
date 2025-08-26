package org.example.msventas.service.impl;

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
public class SaleServiceImpl implements org.example.msventas.service.SaleService {

    private final org.example.msventas.repository.SaleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<org.example.msventas.entity.Sale> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public org.example.msventas.entity.Sale findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
    }

    @Override
    public org.example.msventas.entity.Sale create(org.example.msventas.entity.Sale sale) {
        basicChecks(sale);
        if (sale.getSaleDate() == null) {
            sale.setSaleDate(LocalDateTime.now());
        }
        return repository.save(sale);
    }

    @Override
    public org.example.msventas.entity.Sale update(Long id, org.example.msventas.entity.Sale data) {
        basicChecks(data);
        org.example.msventas.entity.Sale current = findById(id);

        current.setCustomerId(data.getCustomerId());
        current.setSaleDate(data.getSaleDate() != null ? data.getSaleDate() : current.getSaleDate());
        current.setTotalAmount(data.getTotalAmount());
        current.setStatus(data.getStatus());
        current.setNotes(data.getNotes());

        return repository.save(current);
    }

    @Override
    public void delete(Long id) {
        org.example.msventas.entity.Sale existing = findById(id);
        repository.delete(existing);
    }

    // ===== Helpers (sin jakarta.validation) =====
    private void basicChecks(org.example.msventas.entity.Sale s) {
        if (s.getCustomerId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customerId is required");
        }
        if (s.getTotalAmount() == null || s.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "totalAmount must be >= 0");
        }
        if (isBlank(s.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status is required");
        }
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}