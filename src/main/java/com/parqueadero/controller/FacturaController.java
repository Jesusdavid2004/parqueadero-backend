package com.parqueadero.controller;

import com.parqueadero.dto.FacturaDTO;
import com.parqueadero.service.FacturaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public List<FacturaDTO> listar() {
        return facturaService.listar();
    }

    @GetMapping("/{id}")
    public FacturaDTO buscarPorId(@PathVariable Long id) {
        return facturaService.buscarPorId(id);
    }

    @PostMapping
    public FacturaDTO guardar(@RequestBody FacturaDTO dto) {
        return facturaService.guardar(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        facturaService.eliminar(id);
    }
}