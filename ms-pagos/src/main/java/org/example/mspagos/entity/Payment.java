package org.example.mspagos.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia externa: por ejemplo, id de venta (ms-ventas) o factura
    @Column(name = "sale_id", nullable = false)
    private Long saleId;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(name = "method", nullable = false, length = 30)
    private String method; // CASH, CARD, TRANSFER, etc.

    @Column(name = "status", nullable = false, length = 20)
    private String status; // PENDING, PAID, FAILED, REFUNDED

    @Column(name = "reference", length = 80)
    private String reference; // nro operación, voucher, etc.

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}