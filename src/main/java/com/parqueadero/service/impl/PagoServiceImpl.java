package com.parqueadero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parqueadero.dto.PagoDTO;
import com.parqueadero.enums.EstadoPago;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Pago;
import com.parqueadero.model.Ticket;
import com.parqueadero.repository.PagoRepository;
import com.parqueadero.repository.TicketRepository;
import com.parqueadero.service.PagoService;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final TicketRepository ticketRepository;

    public PagoServiceImpl(PagoRepository pagoRepository, TicketRepository ticketRepository) {
        this.pagoRepository = pagoRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<PagoDTO> listar() {
        return pagoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PagoDTO buscarPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
        return toDTO(pago);
    }

    @Override
    public PagoDTO guardar(PagoDTO dto) {
        Pago pago = new Pago();
        map(dto, pago);
        return toDTO(pagoRepository.save(pago));
    }

    @Override
    public void eliminar(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
        pagoRepository.delete(pago);
    }

    private void map(PagoDTO dto, Pago pago) {
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con id: " + dto.getTicketId()));

        if (ticket.getTotal() == null || ticket.getTotal() <= 0) {
            throw new IllegalArgumentException("El ticket " + ticket.getCodigoTicket() + " no tiene total calculado.");
        }

        pago.setMonto(ticket.getTotal()); // el monto viene del ticket, no del DTO
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoPago.PENDIENTE);
        pago.setReferencia(dto.getReferencia());
        pago.setFecha(dto.getFecha());
        pago.setTicket(ticket);
    }

    private PagoDTO toDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setMonto(pago.getMonto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstado(pago.getEstado());
        dto.setReferencia(pago.getReferencia());
        dto.setFecha(pago.getFecha());
        dto.setTicketId(pago.getTicket().getId());
        return dto;
    }
}