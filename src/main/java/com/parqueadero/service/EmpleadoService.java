package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.EmpleadoDTO;

public interface EmpleadoService {
    List<EmpleadoDTO> listar();
    EmpleadoDTO buscarPorId(Long id);
    EmpleadoDTO guardar(EmpleadoDTO dto);
    EmpleadoDTO actualizar(Long id, EmpleadoDTO dto);
    void eliminar(Long id);
}
