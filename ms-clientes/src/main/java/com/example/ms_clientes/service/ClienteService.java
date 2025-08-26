package com.example.ms_clientes.service;

import com.example.ms_clientes.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    Page<Cliente> findAll(Pageable pageable);
    Cliente findById(Long id);
    Cliente create(Cliente cliente);
    Cliente update(Long id, Cliente cliente);
    void delete(Long id);
}