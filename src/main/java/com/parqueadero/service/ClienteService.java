package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.ClienteDTO;

public interface ClienteService {
    List<ClienteDTO> listar();
    ClienteDTO buscarPorId(Long id);
    ClienteDTO guardar(ClienteDTO dto);
    ClienteDTO actualizar(Long id, ClienteDTO dto);
    void eliminar(Long id);
}