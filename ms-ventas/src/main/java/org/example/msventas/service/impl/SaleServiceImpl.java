package org.example.msventas.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.msventas.dto.ClientesDto;
import org.example.msventas.dto.VentasDto;
import org.example.msventas.entity.Sale;
import org.example.msventas.feign.ClienteFeign;
import org.example.msventas.mappers.SaleMapper;
import org.example.msventas.repository.SaleRepository;
import org.example.msventas.service.SaleService;
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
public class SaleServiceImpl implements SaleService {

    private final SaleRepository repository;
    private final ClienteFeign clienteFeign;

    // ====== CRUD ======

    @Override
    @Transactional(readOnly = true)
    public Page<Sale> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public VentasDto findById(Long id) {
        Sale sale = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        // Consumir microservicio de clientes
        ClientesDto cliente = clienteFeign.findById(sale.getCustomerId());

        return SaleMapper.toDto(sale, cliente);
    }

    @Override
    public Sale create(Sale sale) {
        basicChecks(sale);
        if (sale.getSaleDate() == null) {
            sale.setSaleDate(LocalDateTime.now());
        }
        return repository.save(sale);
    }

    @Override
    public Sale update(Long id, Sale data) {
        basicChecks(data);
        Sale current = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        current.setCustomerId(data.getCustomerId());
        current.setSaleDate(data.getSaleDate() != null ? data.getSaleDate() : current.getSaleDate());
        current.setTotalAmount(data.getTotalAmount());
        current.setStatus(data.getStatus());
        current.setNotes(data.getNotes());

        return repository.save(current);
    }

    @Override
    public void delete(Long id) {
        Sale existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        repository.delete(existing);
    }

    // ===== Helpers (sin jakarta.validation) =====
    private void basicChecks(Sale s) {
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
