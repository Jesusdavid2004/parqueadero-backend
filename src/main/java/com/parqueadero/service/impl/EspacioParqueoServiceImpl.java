package com.parqueadero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dto.EspacioParqueoDTO;
import com.parqueadero.enums.EstadoEspacio;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.EspacioParqueo;
import com.parqueadero.model.Zona;
import com.parqueadero.repository.EspacioParqueoRepository;
import com.parqueadero.repository.ZonaRepository;
import com.parqueadero.service.EspacioParqueoService;

@Service
public class EspacioParqueoServiceImpl implements EspacioParqueoService {

    private final EspacioParqueoRepository espacioParqueoRepository;
    private final ZonaRepository zonaRepository;

    public EspacioParqueoServiceImpl(EspacioParqueoRepository espacioParqueoRepository, ZonaRepository zonaRepository) {
        this.espacioParqueoRepository = espacioParqueoRepository;
        this.zonaRepository = zonaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacioParqueoDTO> listar() {
        return espacioParqueoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacioParqueoDTO> listarDisponibles() {
        return espacioParqueoRepository.findByEstado(EstadoEspacio.DISPONIBLE)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EspacioParqueoDTO buscarPorId(Long id) {
        EspacioParqueo espacio = espacioParqueoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio de parqueo no encontrado con id: " + id));
        return toDTO(espacio);
    }

    @Override
    @Transactional
    public EspacioParqueoDTO guardar(EspacioParqueoDTO dto) {
        EspacioParqueo espacio = new EspacioParqueo();
        mapToEntity(dto, espacio);
        return toDTO(espacioParqueoRepository.save(espacio));
    }

    @Override
    @Transactional
    public EspacioParqueoDTO actualizar(Long id, EspacioParqueoDTO dto) {
        EspacioParqueo espacio = espacioParqueoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio de parqueo no encontrado con id: " + id));
        mapToEntity(dto, espacio);
        return toDTO(espacioParqueoRepository.save(espacio));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        EspacioParqueo espacio = espacioParqueoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espacio de parqueo no encontrado con id: " + id));
        espacioParqueoRepository.delete(espacio);
    }

    private void mapToEntity(EspacioParqueoDTO dto, EspacioParqueo espacio) {
        espacio.setCodigo(dto.getCodigo());
        espacio.setUbicacion(dto.getUbicacion());
        espacio.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoEspacio.DISPONIBLE);
        espacio.setTipoVehiculoPermitido(dto.getTipoVehiculoPermitido());

        if (dto.getZonaId() != null) {
            Zona zona = zonaRepository.findById(dto.getZonaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + dto.getZonaId()));
            espacio.setZona(zona);
        }
    }

    private EspacioParqueoDTO toDTO(EspacioParqueo espacio) {
        EspacioParqueoDTO dto = new EspacioParqueoDTO();
        dto.setId(espacio.getId());
        dto.setCodigo(espacio.getCodigo());
        dto.setUbicacion(espacio.getUbicacion());
        dto.setEstado(espacio.getEstado());
        dto.setTipoVehiculoPermitido(espacio.getTipoVehiculoPermitido());

        if (espacio.getZona() != null) {
            dto.setZonaId(espacio.getZona().getId());
            dto.setZonaNombre(espacio.getZona().getNombre());
        }

        return dto;
    }
}