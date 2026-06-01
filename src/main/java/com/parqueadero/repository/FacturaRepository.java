package com.parqueadero.repository;

import com.parqueadero.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByPagoId(Long pagoId);
}