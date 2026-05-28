package com.parqueadero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}