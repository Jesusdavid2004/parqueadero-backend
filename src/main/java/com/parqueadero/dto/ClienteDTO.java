package com.parqueadero.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {
    private Long id;
    private String identificacion;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
}