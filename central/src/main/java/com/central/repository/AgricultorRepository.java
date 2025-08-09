package com.central.repository;

import com.central.entity.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgricultorRepository extends JpaRepository<Agricultor, java.util.UUID> {
}
