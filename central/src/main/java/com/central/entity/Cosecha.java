package com.central.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoCultivo;
    private Double cantidadKg;
    private LocalDate fechaCosecha;
    private String estado; // "registrada" o "facturada"
    @ManyToOne
    @JoinColumn(name = "agricultor_id")
    private Agricultor agricultor;
}
