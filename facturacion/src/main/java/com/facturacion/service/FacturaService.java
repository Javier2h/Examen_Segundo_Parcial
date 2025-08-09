package com.facturacion.service;

import com.facturacion.entity.Factura;
import com.facturacion.event.InventarioAjustadoEvent;
import com.facturacion.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @RabbitListener(queues = "${rabbitmq.queue.inventario_ajustado}")
    @Transactional
    public void handleInventarioAjustado(InventarioAjustadoEvent event) {
        // Calcular monto de la factura
        double precioPorKg = 2.5;
        double monto = event.getCantidadKg() * precioPorKg;
        // Crear y guardar factura
        Factura factura = Factura.builder()
                .cosechaId(event.getCosechaId())
                .montoTotal(monto)
                .fechaFactura(LocalDate.now())
                .detalle("Factura por cosecha " + event.getCosechaId() + ", ajuste: " + event.getAjuste())
                .build();
        facturaRepository.save(factura);
        // Llamar al microservicio central para actualizar estado de la cosecha
        String url = "http://central:8080/cosechas/" + event.getCosechaId();
        restTemplate.put(url, null); // Se recomienda implementar un endpoint específico para actualizar estado
    }
}
