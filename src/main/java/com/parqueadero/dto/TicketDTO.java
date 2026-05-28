package com.parqueadero.dto;

import com.parqueadero.enums.EstadoTicket;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketDTO {
    private Long id;
    private String codigoTicket;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private EstadoTicket estado;
    private Double total;
    private Long vehiculoId;
    private Long espacioId;
}