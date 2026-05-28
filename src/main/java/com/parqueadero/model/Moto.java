package com.parqueadero.model;

import com.parqueadero.enums.TipoVehiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "motos")
@Getter
@Setter
public class Moto extends Vehiculo {

    private Integer cilindraje;
    private Boolean tieneMaletero = false;

    public Moto() {
        this.setTipoVehiculo(TipoVehiculo.MOTO);
    }
}