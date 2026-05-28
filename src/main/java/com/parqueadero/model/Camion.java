package com.parqueadero.model;

import com.parqueadero.enums.TipoVehiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "camiones")
@Getter
@Setter
public class Camion extends Vehiculo {

    private Integer numeroEjes;
    private Double capacidadCarga;

    public Camion() {
        this.setTipoVehiculo(TipoVehiculo.CAMION);
    }
}