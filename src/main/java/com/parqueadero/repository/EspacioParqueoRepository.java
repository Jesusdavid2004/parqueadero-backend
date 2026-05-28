package com.parqueadero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.enums.EstadoEspacio;
import com.parqueadero.model.EspacioParqueo;

public interface EspacioParqueoRepository extends JpaRepository<EspacioParqueo, Long> {

    List<EspacioParqueo> findByEstado(EstadoEspacio estado);
}