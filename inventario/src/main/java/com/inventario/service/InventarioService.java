package com.inventario.service;

import com.inventario.entity.Insumo;
import com.inventario.event.CosechaEvent;
import com.inventario.event.InventarioAjustadoEvent;
import com.inventario.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InsumoRepository insumoRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.inventario_ajustado}")
    private String inventarioAjustadoQueue;

    @RabbitListener(queues = "${rabbitmq.queue.nueva_cosecha}")
    @Transactional
    public void handleNuevaCosecha(CosechaEvent event) {
        // Ajustar inventario de todos los insumos
        List<Insumo> insumos = insumoRepository.findAll();
        double ajuste = event.getCantidadKg() * 0.1;
        for (Insumo insumo : insumos) {
            insumo.setStockActual(insumo.getStockActual() - ajuste);
            insumoRepository.save(insumo);
        }
        // Publicar evento inventario_ajustado
        InventarioAjustadoEvent ajustadoEvent = InventarioAjustadoEvent.builder()
                .cosechaId(event.getId())
                .tipoCultivo(event.getTipoCultivo())
                .cantidadKg(event.getCantidadKg())
                .fechaCosecha(event.getFechaCosecha())
                .estado(event.getEstado())
                .agricultorId(event.getAgricultorId())
                .ajuste(ajuste)
                .build();
        rabbitTemplate.convertAndSend(inventarioAjustadoQueue, ajustadoEvent);
    }
}
