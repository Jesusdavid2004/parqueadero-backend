package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.ZonaDTO;

public interface ZonaService {
    List<ZonaDTO> listar();
    ZonaDTO buscarPorId(Long id);
    ZonaDTO guardar(ZonaDTO dto);
    ZonaDTO actualizar(Long id, ZonaDTO dto);
    void eliminar(Long id);
}
