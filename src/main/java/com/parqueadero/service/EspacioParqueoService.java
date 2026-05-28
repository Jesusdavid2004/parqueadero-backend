package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.EspacioParqueoDTO;

public interface EspacioParqueoService {

    List<EspacioParqueoDTO> listarDisponibles();
}