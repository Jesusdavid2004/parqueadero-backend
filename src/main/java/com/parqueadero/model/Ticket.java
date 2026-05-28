package com.parqueadero.model;

import com.parqueadero.enums.EstadoTicket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoTicket;

    @Column(nullable = false)
    private LocalDateTime horaEntrada;

    private LocalDateTime horaSalida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTicket estado = EstadoTicket.ABIERTO;

    @Column(nullable = false)
    private Double total = 0.0;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "espacio_id", nullable = false)
    private EspacioParqueo espacioParqueo;
}