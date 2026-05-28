package com.parqueadero.dto;

import com.parqueadero.enums.TipoVehiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoDTO {
    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private TipoVehiculo tipoVehiculo;
    private Long clienteId;
}