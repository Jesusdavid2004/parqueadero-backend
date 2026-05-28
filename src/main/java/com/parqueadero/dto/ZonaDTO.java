package com.parqueadero.dto;

import com.parqueadero.enums.TipoZona;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZonaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private TipoZona tipoZona;
    private Long sedeId;
    private String sedeNombre;
}
