package com.parqueadero.dto;

import com.parqueadero.enums.EstadoReserva;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaDTO {
    private Long id;
    private String codigoReserva;
    private LocalDateTime fechaReserva;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private EstadoReserva estado;
    private Long clienteId;
    private Long vehiculoId;
    private Long espacioId;
}