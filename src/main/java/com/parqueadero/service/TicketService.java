package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.TicketDTO;

public interface TicketService {
    List<TicketDTO> listar();
    TicketDTO buscarPorId(Long id);
    TicketDTO guardar(TicketDTO dto);
    TicketDTO actualizar(Long id, TicketDTO dto);
    void eliminar(Long id);
}