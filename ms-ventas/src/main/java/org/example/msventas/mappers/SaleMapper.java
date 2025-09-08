package org.example.msventas.mappers;

import org.example.msventas.dto.ClientesDto;
import org.example.msventas.dto.VentasDto;
import org.example.msventas.entity.Sale;

public class SaleMapper {

    public static VentasDto toDto(Sale sale, ClientesDto cliente) {
        return VentasDto.builder()
                .id(sale.getId())
                .customerId(sale.getCustomerId())
                .saleDate(sale.getSaleDate())
                .totalAmount(sale.getTotalAmount())
                .status(sale.getStatus())
                .notes(sale.getNotes())
                .createdAt(sale.getCreatedAt())
                .updatedAt(sale.getUpdatedAt())
                .clientes(cliente) // aquí metemos el Feign
                .build();
    }
}