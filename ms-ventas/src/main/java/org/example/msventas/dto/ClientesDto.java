package org.example.msventas.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientesDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String documentNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
