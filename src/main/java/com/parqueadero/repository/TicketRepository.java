package com.parqueadero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByVehiculoClienteId(Long clienteId);
}