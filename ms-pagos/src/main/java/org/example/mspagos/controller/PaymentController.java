package org.example.mspagos.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final org.example.mspagos.service.PaymentService service;

    @GetMapping
    public ResponseEntity<Page<org.example.mspagos.entity.Payment>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<org.example.mspagos.entity.Payment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<org.example.mspagos.entity.Payment> create(@RequestBody org.example.mspagos.entity.Payment payment,
                                                                     UriComponentsBuilder uriBuilder) {
        org.example.mspagos.entity.Payment saved = service.create(payment);
        return ResponseEntity
                .created(uriBuilder.path("/api/payments/{id}").buildAndExpand(saved.getId()).toUri())
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<org.example.mspagos.entity.Payment> update(@PathVariable Long id, @RequestBody org.example.mspagos.entity.Payment payment) {
        return ResponseEntity.ok(service.update(id, payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}