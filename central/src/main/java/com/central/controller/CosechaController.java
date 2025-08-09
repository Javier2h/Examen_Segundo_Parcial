package com.central.controller;

import com.central.dto.CosechaDTO;
import com.central.service.CosechaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cosechas")
@RequiredArgsConstructor
public class CosechaController {
    private final CosechaService cosechaService;

    @GetMapping
    public List<CosechaDTO> getAll() {
        return cosechaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CosechaDTO> getById(@PathVariable Long id) {
        return cosechaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CosechaDTO create(@RequestBody CosechaDTO dto) {
        return cosechaService.save(dto);
    }

    @PutMapping("/{id}")
    public CosechaDTO update(@PathVariable Long id, @RequestBody CosechaDTO dto) {
        return cosechaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cosechaService.delete(id);
    }
}
