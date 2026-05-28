package com.parqueadero.service.impl;

import com.parqueadero.dto.VehiculoDTO;
import com.parqueadero.enums.TipoVehiculo;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.*;
import com.parqueadero.repository.ClienteRepository;
import com.parqueadero.repository.VehiculoRepository;
import com.parqueadero.service.VehiculoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, ClienteRepository clienteRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<VehiculoDTO> listar() {
        return vehiculoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public VehiculoDTO buscarPorId(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + id));
        return toDTO(vehiculo);
    }

    @Override
    public VehiculoDTO guardar(VehiculoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + dto.getClienteId()));

        Vehiculo vehiculo = crearInstancia(dto.getTipoVehiculo());
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setColor(dto.getColor());
        vehiculo.setCliente(cliente);

        return toDTO(vehiculoRepository.save(vehiculo));
    }

    @Override
    public VehiculoDTO actualizar(Long id, VehiculoDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + id));
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + dto.getClienteId()));

        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setColor(dto.getColor());
        vehiculo.setCliente(cliente);

        return toDTO(vehiculoRepository.save(vehiculo));
    }

    @Override
    public void eliminar(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + id));
        vehiculoRepository.delete(vehiculo);
    }

    private Vehiculo crearInstancia(TipoVehiculo tipoVehiculo) {
        return switch (tipoVehiculo) {
            case CARRO -> new Carro();
            case MOTO -> new Moto();
            case CAMION -> new Camion();
        };
    }

    private VehiculoDTO toDTO(Vehiculo vehiculo) {
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
}