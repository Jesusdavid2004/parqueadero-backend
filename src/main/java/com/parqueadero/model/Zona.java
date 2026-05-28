package com.parqueadero.model;

import com.parqueadero.enums.TipoZona;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "zonas")
@Getter
@Setter
public class Zona extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoZona tipoZona;

    @ManyToOne
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;
}