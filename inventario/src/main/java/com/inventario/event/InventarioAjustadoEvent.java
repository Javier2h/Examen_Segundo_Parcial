package com.inventario.event;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioAjustadoEvent {
    private Long cosechaId;
    private String tipoCultivo;
    private Double cantidadKg;
    private LocalDate fechaCosecha;
    private String estado;
    private Long agricultorId;
    private Double ajuste;
}
