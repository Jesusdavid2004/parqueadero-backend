package com.parqueadero.model;

import com.parqueadero.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tarifas")
@Getter
@Setter
public class Tarifa extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoVehiculo tipoVehiculo;

    @Column(nullable = false)
    private Double valorHora;

    private Double valorDia;
    private Double valorFraccion;
}