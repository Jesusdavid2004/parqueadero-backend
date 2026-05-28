package com.parqueadero.service;

import com.parqueadero.dto.ReservaDTO;
import java.util.List;

public interface ReservaService {
    List<ReservaDTO> listar();
    ReservaDTO buscarPorId(Long id);
    ReservaDTO guardar(ReservaDTO dto);
    ReservaDTO actualizar(Long id, ReservaDTO dto);
    void eliminar(Long id);
}