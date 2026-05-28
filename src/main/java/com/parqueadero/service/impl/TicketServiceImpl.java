package com.parqueadero.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parqueadero.dto.TicketDTO;
import com.parqueadero.enums.EstadoEspacio;
import com.parqueadero.enums.EstadoTicket;
import com.parqueadero.enums.TipoVehiculo;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.EspacioParqueo;
import com.parqueadero.model.Tarifa;
import com.parqueadero.model.Ticket;
import com.parqueadero.model.Vehiculo;
import com.parqueadero.repository.EspacioParqueoRepository;
import com.parqueadero.repository.TarifaRepository;
import com.parqueadero.repository.TicketRepository;
import com.parqueadero.repository.VehiculoRepository;
import com.parqueadero.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EspacioParqueoRepository espacioRepository;
    private final TarifaRepository tarifaRepository;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            VehiculoRepository vehiculoRepository,
            EspacioParqueoRepository espacioRepository,
            TarifaRepository tarifaRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.espacioRepository = espacioRepository;
        this.tarifaRepository = tarifaRepository;
    }

    @Override
    public List<TicketDTO> listar() {
        return ticketRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO buscarPorId(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con id: " + id));
        return toDTO(ticket);
    }

    @Override
    public TicketDTO guardar(TicketDTO dto) {
        Ticket ticket = new Ticket();
        map(dto, ticket);
        calcularTotalYEstadoEspacio(ticket);
        return toDTO(ticketRepository.save(ticket));
    }

    @Override
    public TicketDTO actualizar(Long id, TicketDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con id: " + id));

        // Si cambia de espacio, liberar el anterior
        if (!ticket.getEspacioParqueo().getId().equals(dto.getEspacioId())) {
            EspacioParqueo anterior = ticket.getEspacioParqueo();
            anterior.setEstado(EstadoEspacio.DISPONIBLE);
            espacioRepository.save(anterior);
        }

        map(dto, ticket);
        calcularTotalYEstadoEspacio(ticket);
        return toDTO(ticketRepository.save(ticket));
    }

    @Override
    public void eliminar(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con id: " + id));

        // Al eliminar ticket, liberar espacio
        EspacioParqueo espacio = ticket.getEspacioParqueo();
        espacio.setEstado(EstadoEspacio.DISPONIBLE);
        espacioRepository.save(espacio);

        ticketRepository.delete(ticket);
    }

    private void map(TicketDTO dto, Ticket ticket) {
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + dto.getVehiculoId()));
        EspacioParqueo espacio = espacioRepository.findById(dto.getEspacioId())
                .orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado con id: " + dto.getEspacioId()));

        // validar compatibilidad tipo vehículo vs espacio
        TipoVehiculo tipoVehiculo = vehiculo.getTipoVehiculo();
        if (!espacio.getTipoVehiculoPermitido().equals(tipoVehiculo)) {
            throw new IllegalArgumentException(
                    "El espacio " + espacio.getCodigo() +
                    " no permite vehículos de tipo " + tipoVehiculo
            );
        }

        ticket.setCodigoTicket(dto.getCodigoTicket());
        ticket.setHoraEntrada(dto.getHoraEntrada());
        ticket.setHoraSalida(dto.getHoraSalida());
        ticket.setEstado(dto.getEstado());
        // el total se recalcula, no se toma del DTO
        ticket.setVehiculo(vehiculo);
        ticket.setEspacioParqueo(espacio);
    }

    private TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setCodigoTicket(ticket.getCodigoTicket());
        dto.setHoraEntrada(ticket.getHoraEntrada());
        dto.setHoraSalida(ticket.getHoraSalida());
        dto.setEstado(ticket.getEstado());
        dto.setTotal(ticket.getTotal());
        dto.setVehiculoId(ticket.getVehiculo().getId());
        dto.setEspacioId(ticket.getEspacioParqueo().getId());
        return dto;
    }

    /**
     * Calcula el total del ticket según tarifa y tiempo,
     * y actualiza el estado del espacio.
     */
    private void calcularTotalYEstadoEspacio(Ticket ticket) {
        Vehiculo vehiculo = ticket.getVehiculo();
        Tarifa tarifa = tarifaRepository.findByTipoVehiculo(vehiculo.getTipoVehiculo())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe tarifa para tipo de vehículo: " + vehiculo.getTipoVehiculo()
                ));

        LocalDateTime entrada = ticket.getHoraEntrada();
        LocalDateTime salida = ticket.getHoraSalida();

        if (entrada == null) {
            throw new IllegalArgumentException("La hora de entrada es obligatoria");
        }

        if (salida == null) {
            // ticket abierto: no calculamos total todavía, solo reservamos espacio
            ticket.setEstado(EstadoTicket.ABIERTO);
            ticket.setTotal(0.0);
            ticket.getEspacioParqueo().setEstado(EstadoEspacio.OCUPADO);
            espacioRepository.save(ticket.getEspacioParqueo());
            return;
        }

        // ticket cerrado / facturable
        long minutos = Duration.between(entrada, salida).toMinutes();
        if (minutos <= 0) {
            minutos = 1;
        }

        double horas = Math.ceil(minutos / 60.0);
        double totalCalculado = horas * tarifa.getValorHora();

        ticket.setTotal(totalCalculado);
        ticket.setEstado(EstadoTicket.CERRADO);

        // al cerrar ticket, marcar espacio DISPONIBLE
        ticket.getEspacioParqueo().setEstado(EstadoEspacio.DISPONIBLE);
        espacioRepository.save(ticket.getEspacioParqueo());
    }
}