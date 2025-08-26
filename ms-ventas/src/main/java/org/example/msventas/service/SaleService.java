package org.example.msventas.service;


import org.example.msventas.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {
    Page<Sale> findAll(Pageable pageable);
    Sale findById(Long id);
    Sale create(Sale sale);
    Sale update(Long id, Sale sale);
    void delete(Long id);
}