package org.example.msventas.feign;

import org.example.msventas.dto.ClientesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes")
public interface ClienteFeign {

    // GET a /clientes/{id}
    @GetMapping("/{id}")
    ClientesDto findById(@PathVariable("id") Long id);
}