package com.parqueadero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dto.SedeDTO;
import com.parqueadero.exception.BusinessException;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Sede;
import com.parqueadero.repository.SedeRepository;
import com.parqueadero.service.SedeService;

@Service
public class SedeServiceImpl implements SedeService {

    private final SedeRepository sedeRepository;

    public SedeServiceImpl(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SedeDTO> listar() {
        return sedeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SedeDTO buscarPorId(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + id));
        return toDTO(sede);
    }

    @Override
    @Transactional
    public SedeDTO guardar(SedeDTO dto) {
        Sede sede = new Sede();
        mapToEntity(dto, sede);
        return toDTO(sedeRepository.save(sede));
    }

    @Override
    @Transactional
    public SedeDTO actualizar(Long id, SedeDTO dto) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + id));
        mapToEntity(dto, sede);
        return toDTO(sedeRepository.save(sede));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + id));
        sedeRepository.delete(sede);
    }

    private void mapToEntity(SedeDTO dto, Sede sede) {
        sede.setNombre(dto.getNombre());
        sede.setDireccion(dto.getDireccion());
        sede.setCiudad(dto.getCiudad());
    }

    private SedeDTO toDTO(Sede sede) {
        SedeDTO dto = new SedeDTO();
        dto.setId(sede.getId());
        dto.setNombre(sede.getNombre());
        dto.setDireccion(sede.getDireccion());
        dto.setCiudad(sede.getCiudad());
        return dto;
    }
}
