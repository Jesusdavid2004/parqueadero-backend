package com.parqueadero.dto;

import com.parqueadero.enums.EstadoEspacio;
import com.parqueadero.enums.TipoVehiculo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EspacioParqueoDTO {

    private Long id;
    private String codigo;
    private String ubicacion;
    private EstadoEspacio estado;
    private TipoVehiculo tipoVehiculoPermitido;
    private Long zonaId;
    private String zonaNombre;
}