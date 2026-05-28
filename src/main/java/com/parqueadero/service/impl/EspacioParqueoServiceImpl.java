package com.parqueadero.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parqueadero.dto.EspacioParqueoDTO;
import com.parqueadero.enums.EstadoEspacio;
import com.parqueadero.model.EspacioParqueo;
import com.parqueadero.repository.EspacioParqueoRepository;
import com.parqueadero.service.EspacioParqueoService;

@Service
public class EspacioParqueoServiceImpl implements EspacioParqueoService {

    private final EspacioParqueoRepository espacioParqueoRepository;

    public EspacioParqueoServiceImpl(EspacioParqueoRepository espacioParqueoRepository) {
        this.espacioParqueoRepository = espacioParqueoRepository;
    }

    @Override
    public List<EspacioParqueoDTO> listarDisponibles() {
        return espacioParqueoRepository.findByEstado(EstadoEspacio.DISPONIBLE)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private EspacioParqueoDTO toDTO(EspacioParqueo espacio) {
        EspacioParqueoDTO dto = new EspacioParqueoDTO();
        dto.setId(espacio.getId());
        dto.setCodigo(espacio.getCodigo());
        dto.setUbicacion(espacio.getUbicacion());
        dto.setEstado(espacio.getEstado());
        dto.setTipoVehiculoPermitido(espacio.getTipoVehiculoPermitido());

        if (espacio.getZona() != null) {
            dto.setZonaId(espacio.getZona().getId());
            dto.setZonaNombre(espacio.getZona().getNombre());
        }

        return dto;
    }
}