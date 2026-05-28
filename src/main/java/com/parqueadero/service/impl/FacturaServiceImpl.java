package com.parqueadero.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parqueadero.dto.FacturaDTO;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Factura;
import com.parqueadero.model.Pago;
import com.parqueadero.repository.FacturaRepository;
import com.parqueadero.repository.PagoRepository;
import com.parqueadero.service.FacturaService;

@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final PagoRepository pagoRepository;

    // contador simple de ejemplo, en producción usarías una secuencia o tabla dedicada
    private final AtomicLong consecutivo = new AtomicLong(1);

    private static final BigDecimal IVA = new BigDecimal("0.19");

    public FacturaServiceImpl(FacturaRepository facturaRepository, PagoRepository pagoRepository) {
        this.facturaRepository = facturaRepository;
        this.pagoRepository = pagoRepository;
    }

    @Override
    public List<FacturaDTO> listar() {
        return facturaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FacturaDTO buscarPorId(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id " + id));
        return toDTO(factura);
    }

    @Override
    public FacturaDTO guardar(FacturaDTO dto) {
        Factura factura = new Factura();
        map(dto, factura);
        return toDTO(facturaRepository.save(factura));
    }

    @Override
    public void eliminar(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id " + id));
        facturaRepository.delete(factura);
    }

    private void map(FacturaDTO dto, Factura factura) {
        Pago pago = pagoRepository.findById(dto.getPagoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id " + dto.getPagoId()));

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new IllegalArgumentException("El pago " + pago.getId() + " no tiene un monto válido.");
        }

        BigDecimal monto = BigDecimal.valueOf(pago.getMonto());
        BigDecimal subtotal = monto.divide(BigDecimal.ONE.add(IVA), 2, RoundingMode.HALF_UP);
        BigDecimal impuesto = monto.subtract(subtotal);

        // generar número de factura simple: FAC-000001, FAC-000002...
        String numero = String.format("FAC-%06d", consecutivo.getAndIncrement());

        factura.setNumeroFactura(numero);
        factura.setSubtotal(subtotal.doubleValue());
        factura.setImpuesto(impuesto.doubleValue());
        factura.setTotal(monto.doubleValue());
        factura.setPago(pago);
    }

    private FacturaDTO toDTO(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setNumeroFactura(factura.getNumeroFactura());
        dto.setSubtotal(factura.getSubtotal());
        dto.setImpuesto(factura.getImpuesto());
        dto.setTotal(factura.getTotal());
        dto.setPagoId(factura.getPago().getId());
        return dto;
    }
}