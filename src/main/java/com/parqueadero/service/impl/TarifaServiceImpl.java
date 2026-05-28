package com.parqueadero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dto.TarifaDTO;
import com.parqueadero.exception.BusinessException;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Tarifa;
import com.parqueadero.repository.TarifaRepository;
import com.parqueadero.service.TarifaService;

@Service
public class TarifaServiceImpl implements TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaServiceImpl(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TarifaDTO> listar() {
        return tarifaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TarifaDTO buscarPorId(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + id));
        return toDTO(tarifa);
    }

    @Override
    @Transactional
    public TarifaDTO guardar(TarifaDTO dto) {
        tarifaRepository.findByTipoVehiculo(dto.getTipoVehiculo())
                .ifPresent(existing -> {
                    throw new BusinessException("Ya existe una tarifa para el tipo de vehículo: " + dto.getTipoVehiculo());
                });

        Tarifa tarifa = new Tarifa();
        mapToEntity(dto, tarifa);
        return toDTO(tarifaRepository.save(tarifa));
    }

    @Override
    @Transactional
    public TarifaDTO actualizar(Long id, TarifaDTO dto) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + id));
        mapToEntity(dto, tarifa);
        return toDTO(tarifaRepository.save(tarifa));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + id));
        tarifaRepository.delete(tarifa);
    }

    private void mapToEntity(TarifaDTO dto, Tarifa tarifa) {
        tarifa.setTipoVehiculo(dto.getTipoVehiculo());
        tarifa.setValorHora(dto.getValorHora());
        tarifa.setValorDia(dto.getValorDia());
        tarifa.setValorFraccion(dto.getValorFraccion());
    }

    private TarifaDTO toDTO(Tarifa tarifa) {
        TarifaDTO dto = new TarifaDTO();
        dto.setId(tarifa.getId());
        dto.setTipoVehiculo(tarifa.getTipoVehiculo());
        dto.setValorHora(tarifa.getValorHora());
        dto.setValorDia(tarifa.getValorDia());
        dto.setValorFraccion(tarifa.getValorFraccion());
        return dto;
    }
}
