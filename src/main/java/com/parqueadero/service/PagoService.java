package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.PagoDTO;

public interface PagoService {
    List<PagoDTO> listar();
    PagoDTO buscarPorId(Long id);
    PagoDTO guardar(PagoDTO dto);
    void eliminar(Long id);
}