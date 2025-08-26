package com.example.ms_clientes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "clientes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_client_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_client_doc", columnNames = "document_number")
        }
)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No usamos @NotBlank (no tienes validation), controlaremos en el servicio
    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "document_number", nullable = false, length = 20)
    private String documentNumber;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
