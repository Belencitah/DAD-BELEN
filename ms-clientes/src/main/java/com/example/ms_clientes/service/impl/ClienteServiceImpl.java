package com.example.ms_clientes.service.impl;
import com.example.ms_clientes.entity.Cliente;

import com.example.ms_clientes.repository.ClienteRepository;

import com.example.ms_clientes.service.ClienteService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @Override
    public Cliente create(Cliente cliente) {
        basicChecks(cliente); // validaciones mínimas sin jakarta.validation

        if (repository.existsByEmail(cliente.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }
        if (repository.existsByDocumentNumber(cliente.getDocumentNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Document number already in use");
        }
        return repository.save(cliente);
    }

    @Override
    public Cliente update(Long id, Cliente data) {
        basicChecks(data);
        Cliente current = findById(id);

        if (repository.existsByEmailAndIdNot(data.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }
        if (repository.existsByDocumentNumberAndIdNot(data.getDocumentNumber(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Document number already in use");
        }

        current.setFirstName(data.getFirstName());
        current.setLastName(data.getLastName());
        current.setEmail(data.getEmail());
        current.setPhone(data.getPhone());
        current.setDocumentNumber(data.getDocumentNumber());

        return repository.save(current);
    }

    @Override
    public void delete(Long id) {
        Cliente existing = findById(id);
        repository.delete(existing);
    }

    // ====== Helpers ======
    private void basicChecks(Cliente c) {
        if (isBlank(c.getFirstName()) || isBlank(c.getLastName())
                || isBlank(c.getEmail()) || isBlank(c.getDocumentNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "firstName, lastName, email and documentNumber are required");
        }
        // Puedes agregar chequeos simples de formato si deseas
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}