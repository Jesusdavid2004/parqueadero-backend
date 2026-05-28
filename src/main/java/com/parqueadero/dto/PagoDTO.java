package com.parqueadero.dto;

import com.parqueadero.enums.EstadoPago;
import com.parqueadero.enums.MetodoPago;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PagoDTO {
    private Long id;
    private Double monto;
    private MetodoPago metodoPago;
    private EstadoPago estado;
    private String referencia;
    private LocalDateTime fecha;
    private Long ticketId;
}