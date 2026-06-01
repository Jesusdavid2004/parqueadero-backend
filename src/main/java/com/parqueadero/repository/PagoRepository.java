package com.parqueadero.repository;

import com.parqueadero.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByTicketId(Long ticketId);
}