package com.parqueadero.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parqueadero.dto.ClienteDTO;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Cliente;
import com.parqueadero.model.Factura;
import com.parqueadero.model.Pago;
import com.parqueadero.model.Reserva;
import com.parqueadero.model.Ticket;
import com.parqueadero.model.Usuario;
import com.parqueadero.model.Vehiculo;
import com.parqueadero.repository.ClienteRepository;
import com.parqueadero.repository.FacturaRepository;
import com.parqueadero.repository.PagoRepository;
import com.parqueadero.repository.ReservaRepository;
import com.parqueadero.repository.TicketRepository;
import com.parqueadero.repository.UsuarioRepository;
import com.parqueadero.repository.VehiculoRepository;
import com.parqueadero.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TicketRepository ticketRepository;
    private final PagoRepository pagoRepository;
    private final FacturaRepository facturaRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteServiceImpl(
            ClienteRepository clienteRepository,
            ReservaRepository reservaRepository,
            VehiculoRepository vehiculoRepository,
            TicketRepository ticketRepository,
            PagoRepository pagoRepository,
            FacturaRepository facturaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.ticketRepository = ticketRepository;
        this.pagoRepository = pagoRepository;
        this.facturaRepository = facturaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<ClienteDTO> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return toDTO(cliente);
    }

    @Override
    public ClienteDTO guardar(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        mapToEntity(dto, cliente);
        return toDTO(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        mapToEntity(dto, cliente);
        return toDTO(clienteRepository.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        // 1. Eliminar reservas del cliente
        List<Reserva> reservas = reservaRepository.findByClienteId(id);
        reservaRepository.deleteAll(reservas);

        // 2. Eliminar tickets y sus dependencias (pagos y facturas) para cada vehículo
        List<Vehiculo> vehiculos = vehiculoRepository.findByClienteId(id);
        for (Vehiculo vehiculo : vehiculos) {
            List<Ticket> tickets = ticketRepository.findByVehiculoId(vehiculo.getId());
            for (Ticket ticket : tickets) {
                // Eliminar facturas asociadas al pago de este ticket
                List<Pago> pagos = pagoRepository.findByTicketId(ticket.getId());
                for (Pago pago : pagos) {
                    List<Factura> facturas = facturaRepository.findByPagoId(pago.getId());
                    facturaRepository.deleteAll(facturas);
                }
                pagoRepository.deleteAll(pagos);
            }
            ticketRepository.deleteAll(tickets);
        }

        // 3. Eliminar vehículos del cliente
        vehiculoRepository.deleteAll(vehiculos);

        // 4. Desasociar usuario (si existe)
        usuarioRepository.findByClienteId(id).ifPresent(usuario -> {
            usuario.setCliente(null);
            usuarioRepository.save(usuario);
        });

        // 5. Eliminar cliente
        clienteRepository.delete(cliente);
    }

    private void mapToEntity(ClienteDTO dto, Cliente cliente) {
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreo(dto.getCorreo());
        cliente.setDireccion(dto.getDireccion());
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setNombre(cliente.getNombre());
        dto.setTelefono(cliente.getTelefono());
        dto.setCorreo(cliente.getCorreo());
        dto.setDireccion(cliente.getDireccion());
        return dto;
    }
}