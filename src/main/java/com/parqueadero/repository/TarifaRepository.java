package com.parqueadero.repository;

import com.parqueadero.enums.TipoVehiculo;
import com.parqueadero.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    Optional<Tarifa> findByTipoVehiculo(TipoVehiculo tipoVehiculo);
}