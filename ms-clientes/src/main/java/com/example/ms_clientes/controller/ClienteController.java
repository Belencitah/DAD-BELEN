package com.example.ms_clientes.controller;

import com.example.ms_clientes.entity.Cliente;
import com.example.ms_clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public ResponseEntity<Page<Cliente>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente,
                                          UriComponentsBuilder uriBuilder) {
        Cliente saved = service.create(cliente);
        return ResponseEntity
                .created(uriBuilder.path("/api/clientes/{id}").buildAndExpand(saved.getId()).toUri())
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.update(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}