package com.parqueadero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parqueadero.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByPadreIsNull();

    List<Menu> findByPadreId(Long padreId);

    // Trae los menús raíz y sus hijos en una sola consulta para evitar problemas de lazy loading
    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.hijos WHERE m.padre IS NULL")
    List<Menu> findRaizConHijos();
}