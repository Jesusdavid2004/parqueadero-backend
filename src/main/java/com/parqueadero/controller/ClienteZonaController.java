package com.parqueadero.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.dto.TicketDTO;
import com.parqueadero.dto.VehiculoDTO;
import com.parqueadero.model.Ticket;
import com.parqueadero.model.Usuario;
import com.parqueadero.model.Vehiculo;
import com.parqueadero.repository.TicketRepository;
import com.parqueadero.repository.UsuarioRepository;
import com.parqueadero.repository.VehiculoRepository;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin
public class ClienteZonaController {

    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TicketRepository ticketRepository;

    public ClienteZonaController(
            UsuarioRepository usuarioRepository,
            VehiculoRepository vehiculoRepository,
            TicketRepository ticketRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.ticketRepository = ticketRepository;
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = usuarioRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getCliente() == null) {
            throw new RuntimeException("El usuario autenticado no está asociado a un cliente");
        }

        return usuario;
    }

    @GetMapping("/vehiculos")
    public List<VehiculoDTO> vehiculos() {
        Usuario usuario = obtenerUsuarioAutenticado();
        Long clienteId = usuario.getCliente().getId();

        return vehiculoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toVehiculoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/tickets")
    public List<TicketDTO> tickets() {
        Usuario usuario = obtenerUsuarioAutenticado();
        Long clienteId = usuario.getCliente().getId();

        return ticketRepository.findByVehiculoClienteId(clienteId)
                .stream()
                .map(this::toTicketDTO)
                .collect(Collectors.toList());
    }

    private VehiculoDTO toVehiculoDTO(Vehiculo vehiculo) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setId(vehiculo.getId());
        dto.setPlaca(vehiculo.getPlaca());
        dto.setMarca(vehiculo.getMarca());
        dto.setModelo(vehiculo.getModelo());
        dto.setColor(vehiculo.getColor());
        dto.setTipoVehiculo(vehiculo.getTipoVehiculo());
        dto.setClienteId(vehiculo.getCliente().getId());
        return dto;
    }

    private TicketDTO toTicketDTO(Ticket ticket) {
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
}