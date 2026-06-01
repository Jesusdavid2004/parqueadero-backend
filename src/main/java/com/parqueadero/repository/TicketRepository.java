package com.parqueadero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parqueadero.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByVehiculoClienteId(Long clienteId);
    List<Ticket> findByVehiculoId(Long vehiculoId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.vehiculo.cliente.id = :clienteId")
    long countByVehiculoClienteId(@Param("clienteId") Long clienteId);
}