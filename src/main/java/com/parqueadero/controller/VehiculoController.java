package com.parqueadero.controller;

import com.parqueadero.dto.VehiculoDTO;
import com.parqueadero.service.VehiculoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public List<VehiculoDTO> listar() {
        return vehiculoService.listar();
    }

    @GetMapping("/{id}")
    public VehiculoDTO buscarPorId(@PathVariable Long id) {
        return vehiculoService.buscarPorId(id);
    }

    @PostMapping
    public VehiculoDTO guardar(@RequestBody VehiculoDTO dto) {
        return vehiculoService.guardar(dto);
    }

    @PutMapping("/{id}")
    public VehiculoDTO actualizar(@PathVariable Long id, @RequestBody VehiculoDTO dto) {
        return vehiculoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
    }
}