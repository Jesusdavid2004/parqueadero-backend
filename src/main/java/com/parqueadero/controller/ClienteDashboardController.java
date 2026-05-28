package com.parqueadero.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.dto.ClienteDashboardResponse;
import com.parqueadero.model.Usuario;
import com.parqueadero.repository.TicketRepository;
import com.parqueadero.repository.UsuarioRepository;
import com.parqueadero.repository.VehiculoRepository;

@RestController
@RequestMapping("/api/cliente")
public class ClienteDashboardController {

    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TicketRepository ticketRepository;

    public ClienteDashboardController(
            UsuarioRepository usuarioRepository,
            VehiculoRepository vehiculoRepository,
            TicketRepository ticketRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/dashboard")
    public ClienteDashboardResponse dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = usuarioRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getCliente() == null) {
            throw new RuntimeException("El usuario autenticado no está asociado a un cliente");
        }

        Long clienteId = usuario.getCliente().getId();

        long totalVehiculos = vehiculoRepository.findByClienteId(clienteId).size();
        long totalTickets = ticketRepository.findByVehiculoClienteId(clienteId).size();

        return new ClienteDashboardResponse(
                clienteId,
                usuario.getCliente().getNombre(),
                totalVehiculos,
                totalTickets,
                "Dashboard del cliente cargado correctamente"
        );
    }
}