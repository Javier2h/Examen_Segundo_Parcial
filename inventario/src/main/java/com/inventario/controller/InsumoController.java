package com.inventario.controller;

import com.inventario.entity.Insumo;
import com.inventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insumos")
@RequiredArgsConstructor
public class InsumoController {
    private final InsumoRepository insumoRepository;

    @GetMapping
    public List<Insumo> getAll() {
        return insumoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumo> getById(@PathVariable Long id) {
        return insumoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Insumo create(@RequestBody Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    @PutMapping("/{id}")
    public Insumo update(@PathVariable Long id, @RequestBody Insumo insumo) {
        insumo.setId(id);
        return insumoRepository.save(insumo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        insumoRepository.deleteById(id);
    }
}
