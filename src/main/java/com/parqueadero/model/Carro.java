package com.parqueadero.model;

import com.parqueadero.enums.TipoVehiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carros")
@Getter
@Setter
public class Carro extends Vehiculo {

    private Integer numeroPuertas;

    public Carro() {
        this.setTipoVehiculo(TipoVehiculo.CARRO);
    }
}