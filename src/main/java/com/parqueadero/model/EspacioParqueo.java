package com.parqueadero.model;

import com.parqueadero.enums.EstadoEspacio;
import com.parqueadero.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "espacios_parqueo")
@Getter
@Setter
public class EspacioParqueo extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEspacio estado = EstadoEspacio.DISPONIBLE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipoVehiculoPermitido;

    @ManyToOne
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;
}