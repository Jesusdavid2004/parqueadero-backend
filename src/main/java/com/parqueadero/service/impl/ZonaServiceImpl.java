package com.parqueadero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dto.ZonaDTO;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Sede;
import com.parqueadero.model.Zona;
import com.parqueadero.repository.SedeRepository;
import com.parqueadero.repository.ZonaRepository;
import com.parqueadero.service.ZonaService;

@Service
public class ZonaServiceImpl implements ZonaService {

    private final ZonaRepository zonaRepository;
    private final SedeRepository sedeRepository;

    public ZonaServiceImpl(ZonaRepository zonaRepository, SedeRepository sedeRepository) {
        this.zonaRepository = zonaRepository;
        this.sedeRepository = sedeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZonaDTO> listar() {
        return zonaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ZonaDTO buscarPorId(Long id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + id));
        return toDTO(zona);
    }

    @Override
    @Transactional
    public ZonaDTO guardar(ZonaDTO dto) {
        Zona zona = new Zona();
        mapToEntity(dto, zona);
        return toDTO(zonaRepository.save(zona));
    }

    @Override
    @Transactional
    public ZonaDTO actualizar(Long id, ZonaDTO dto) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + id));
        mapToEntity(dto, zona);
        return toDTO(zonaRepository.save(zona));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Zona zona = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + id));
        zonaRepository.delete(zona);
    }

    private void mapToEntity(ZonaDTO dto, Zona zona) {
        Sede sede = sedeRepository.findById(dto.getSedeId())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + dto.getSedeId()));

        zona.setNombre(dto.getNombre());
        zona.setDescripcion(dto.getDescripcion());
        zona.setTipoZona(dto.getTipoZona());
        zona.setSede(sede);
    }

    private ZonaDTO toDTO(Zona zona) {
        ZonaDTO dto = new ZonaDTO();
        dto.setId(zona.getId());
        dto.setNombre(zona.getNombre());
        dto.setDescripcion(zona.getDescripcion());
        dto.setTipoZona(zona.getTipoZona());

        if (zona.getSede() != null) {
            dto.setSedeId(zona.getSede().getId());
            dto.setSedeNombre(zona.getSede().getNombre());
        }

        return dto;
    }
}
