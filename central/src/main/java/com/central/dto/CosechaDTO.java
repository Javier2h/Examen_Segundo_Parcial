package com.central.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CosechaDTO {
    private Long id;
    private String tipoCultivo;
    private Double cantidadKg;
    private LocalDate fechaCosecha;
    private String estado;
    private java.util.UUID agricultorId;
}
