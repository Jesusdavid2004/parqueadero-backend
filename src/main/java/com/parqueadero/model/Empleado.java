package com.parqueadero.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "empleados")
@Getter
@Setter
public class Empleado extends Persona {

    @Column(nullable = false, unique = true)
    private String codigoEmpleado;

    @Column(nullable = false)
    private String cargo;
}