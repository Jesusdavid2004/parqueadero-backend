package com.parqueadero.dto;

import com.parqueadero.enums.TipoVehiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarifaDTO {
    private Long id;
    private TipoVehiculo tipoVehiculo;
    private Double valorHora;
    private Double valorDia;
    private Double valorFraccion;
}
