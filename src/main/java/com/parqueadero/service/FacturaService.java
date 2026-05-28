package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.FacturaDTO;

public interface FacturaService {
    List<FacturaDTO> listar();
    FacturaDTO buscarPorId(Long id);
    FacturaDTO guardar(FacturaDTO dto);
    void eliminar(Long id);
}