package com.parqueadero.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "facturas")
@Getter
@Setter
public class Factura extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String numeroFactura;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double impuesto;

    @Column(nullable = false)
    private Double total;

    @OneToOne
    @JoinColumn(name = "pago_id", nullable = false, unique = true)
    private Pago pago;
}