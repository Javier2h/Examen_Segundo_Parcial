package com.central.service;

import com.central.dto.CosechaDTO;
import com.central.entity.Agricultor;
import com.central.entity.Cosecha;
import com.central.event.CosechaEvent;
import com.central.repository.AgricultorRepository;
import com.central.repository.CosechaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CosechaService {
    private final CosechaRepository cosechaRepository;
    private final AgricultorRepository agricultorRepository;
    private final RabbitMQProducer rabbitMQProducer;

    public List<CosechaDTO> findAll() {
        return cosechaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<CosechaDTO> findById(Long id) {
        return cosechaRepository.findById(id).map(this::toDTO);
    }


    @Transactional
    public CosechaDTO save(CosechaDTO dto) {
    Agricultor agricultor = agricultorRepository.findById(dto.getAgricultorId())
        .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));
    Cosecha cosecha = Cosecha.builder()
        .tipoCultivo(dto.getTipoCultivo())
        .cantidadKg(dto.getCantidadKg())
        .fechaCosecha(dto.getFechaCosecha())
        .estado("registrada")
        .agricultor(agricultor)
        .build();
    cosecha = cosechaRepository.save(cosecha);
    // Publicar evento en RabbitMQ
    rabbitMQProducer.sendNuevaCosechaEvent(toEvent(cosecha));
    return toDTO(cosecha);
    }

    public void delete(Long id) {
        cosechaRepository.deleteById(id);
    }

    public CosechaDTO update(Long id, CosechaDTO dto) {
        Cosecha cosecha = cosechaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cosecha no encontrada"));
        cosecha.setTipoCultivo(dto.getTipoCultivo());
        cosecha.setCantidadKg(dto.getCantidadKg());
        cosecha.setFechaCosecha(dto.getFechaCosecha());
        cosecha.setEstado(dto.getEstado());
        if (dto.getAgricultorId() != null) {
            Agricultor agricultor = agricultorRepository.findById(dto.getAgricultorId())
                    .orElseThrow(() -> new RuntimeException("Agricultor no encontrado"));
            cosecha.setAgricultor(agricultor);
        }
        cosecha = cosechaRepository.save(cosecha);
        return toDTO(cosecha);
    }

    private CosechaDTO toDTO(Cosecha c) {
        return CosechaDTO.builder()
                .id(c.getId())
                .tipoCultivo(c.getTipoCultivo())
                .cantidadKg(c.getCantidadKg())
                .fechaCosecha(c.getFechaCosecha())
                .estado(c.getEstado())
                .agricultorId(c.getAgricultor() != null ? c.getAgricultor().getId() : null)
                .build();
    }

    private CosechaEvent toEvent(Cosecha c) {
        return CosechaEvent.builder()
                .id(c.getId())
                .tipoCultivo(c.getTipoCultivo())
                .cantidadKg(c.getCantidadKg())
                .fechaCosecha(c.getFechaCosecha())
                .estado(c.getEstado())
                .agricultorId(c.getAgricultor() != null ? c.getAgricultor().getId() : null)
                .build();
    }
}
