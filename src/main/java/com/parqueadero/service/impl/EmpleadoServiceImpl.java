package com.parqueadero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dto.EmpleadoDTO;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Empleado;
import com.parqueadero.repository.EmpleadoRepository;
import com.parqueadero.service.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> listar() {
        return empleadoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoDTO buscarPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        return toDTO(empleado);
    }

    @Override
    @Transactional
    public EmpleadoDTO guardar(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        mapToEntity(dto, empleado);
        return toDTO(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public EmpleadoDTO actualizar(Long id, EmpleadoDTO dto) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        mapToEntity(dto, empleado);
        return toDTO(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        empleadoRepository.delete(empleado);
    }

    private void mapToEntity(EmpleadoDTO dto, Empleado empleado) {
        empleado.setIdentificacion(dto.getIdentificacion());
        empleado.setNombre(dto.getNombre());
        empleado.setTelefono(dto.getTelefono());
        empleado.setCorreo(dto.getCorreo());
        empleado.setCodigoEmpleado(dto.getCodigoEmpleado());
        empleado.setCargo(dto.getCargo());
    }

    private EmpleadoDTO toDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setIdentificacion(empleado.getIdentificacion());
        dto.setNombre(empleado.getNombre());
        dto.setTelefono(empleado.getTelefono());
        dto.setCorreo(empleado.getCorreo());
        dto.setCodigoEmpleado(empleado.getCodigoEmpleado());
        dto.setCargo(empleado.getCargo());
        return dto;
    }
}
