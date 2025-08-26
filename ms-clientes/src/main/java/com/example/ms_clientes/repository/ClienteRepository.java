package com.example.ms_clientes.repository;

import com.example.ms_clientes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
    boolean existsByDocumentNumber(String documentNumber);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByDocumentNumberAndIdNot(String documentNumber, Long id);
}