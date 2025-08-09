package com.facturacion.controller;

import com.facturacion.entity.Factura;
import com.facturacion.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
public class FacturaController {
    private final FacturaRepository facturaRepository;

    @GetMapping
    public List<Factura> getAll() {
        return facturaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> getById(@PathVariable Long id) {
        return facturaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        facturaRepository.deleteById(id);
    }
}
