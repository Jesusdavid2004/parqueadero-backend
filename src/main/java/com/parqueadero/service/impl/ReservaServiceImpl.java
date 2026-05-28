package com.parqueadero.service.impl;

import com.parqueadero.dto.ReservaDTO;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Cliente;
import com.parqueadero.model.EspacioParqueo;
import com.parqueadero.model.Reserva;
import com.parqueadero.model.Vehiculo;
import com.parqueadero.repository.ClienteRepository;
import com.parqueadero.repository.EspacioParqueoRepository;
import com.parqueadero.repository.ReservaRepository;
import com.parqueadero.repository.VehiculoRepository;
import com.parqueadero.service.ReservaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EspacioParqueoRepository espacioRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, ClienteRepository clienteRepository,
                              VehiculoRepository vehiculoRepository, EspacioParqueoRepository espacioRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.espacioRepository = espacioRepository;
    }

    @Override
    public List<ReservaDTO> listar() {
        return reservaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ReservaDTO buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        return toDTO(reserva);
    }

    @Override
    public ReservaDTO guardar(ReservaDTO dto) {
        Reserva reserva = new Reserva();
        map(dto, reserva);
        return toDTO(reservaRepository.save(reserva));
    }

    @Override
    public ReservaDTO actualizar(Long id, ReservaDTO dto) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        map(dto, reserva);
        return toDTO(reservaRepository.save(reserva));
    }

    @Override
    public void eliminar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        reservaRepository.delete(reserva);
    }

    private void map(ReservaDTO dto, Reserva reserva) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + dto.getClienteId()));
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + dto.getVehiculoId()));
        EspacioParqueo espacio = espacioRepository.findById(dto.getEspacioId())
                .orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado con id: " + dto.getEspacioId()));

        reserva.setCodigoReserva(dto.getCodigoReserva());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setHoraInicio(dto.getHoraInicio());
        reserva.setHoraFin(dto.getHoraFin());
        reserva.setEstado(dto.getEstado());
        reserva.setCliente(cliente);
        reserva.setVehiculo(vehiculo);
        reserva.setEspacioParqueo(espacio);
    }

    private ReservaDTO toDTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setCodigoReserva(reserva.getCodigoReserva());
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setHoraInicio(reserva.getHoraInicio());
        dto.setHoraFin(reserva.getHoraFin());
        dto.setEstado(reserva.getEstado());
        dto.setClienteId(reserva.getCliente().getId());
        dto.setVehiculoId(reserva.getVehiculo().getId());
        dto.setEspacioId(reserva.getEspacioParqueo().getId());
        return dto;
    }
}