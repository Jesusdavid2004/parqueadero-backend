package com.parqueadero.service;

import com.parqueadero.dto.VehiculoDTO;
import java.util.List;

public interface VehiculoService {
    List<VehiculoDTO> listar();
    VehiculoDTO buscarPorId(Long id);
    VehiculoDTO guardar(VehiculoDTO dto);
    VehiculoDTO actualizar(Long id, VehiculoDTO dto);
    void eliminar(Long id);
}