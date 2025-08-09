package com.central.controller;

import com.central.entity.Agricultor;
import com.central.repository.AgricultorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agricultores")
@RequiredArgsConstructor
public class AgricultorController {
    private final AgricultorRepository agricultorRepository;

    @GetMapping
    public List<Agricultor> getAll() {
        return agricultorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agricultor> getById(@PathVariable UUID id) {
        return agricultorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Agricultor create(@RequestBody Agricultor agricultor) {
        return agricultorRepository.save(agricultor);
    }

    @PutMapping("/{id}")
    public Agricultor update(@PathVariable UUID id, @RequestBody Agricultor agricultor) {
        agricultor.setId(id);
        return agricultorRepository.save(agricultor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        agricultorRepository.deleteById(id);
    }
}
