package org.example.msventas.feign;

import org.example.msventas.dto.ClientesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes", path = "/clientes")
public interface ClienteFeign {
    public ResponseEntity<ClientesDto> findById(@PathVariable Long id);
}
