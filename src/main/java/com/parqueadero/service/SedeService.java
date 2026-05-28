package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.SedeDTO;

public interface SedeService {
    List<SedeDTO> listar();
    SedeDTO buscarPorId(Long id);
    SedeDTO guardar(SedeDTO dto);
    SedeDTO actualizar(Long id, SedeDTO dto);
    void eliminar(Long id);
}
