package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.EspacioParqueoDTO;

public interface EspacioParqueoService {

    List<EspacioParqueoDTO> listar();
    List<EspacioParqueoDTO> listarDisponibles();
    EspacioParqueoDTO buscarPorId(Long id);
    EspacioParqueoDTO guardar(EspacioParqueoDTO dto);
    EspacioParqueoDTO actualizar(Long id, EspacioParqueoDTO dto);
    void eliminar(Long id);
}