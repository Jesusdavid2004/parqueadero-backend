package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.TarifaDTO;

public interface TarifaService {
    List<TarifaDTO> listar();
    TarifaDTO buscarPorId(Long id);
    TarifaDTO guardar(TarifaDTO dto);
    TarifaDTO actualizar(Long id, TarifaDTO dto);
    void eliminar(Long id);
}
