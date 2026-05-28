package com.parqueadero.controller;

import com.parqueadero.dto.ReservaDTO;
import com.parqueadero.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<ReservaDTO> listar() {
        return reservaService.listar();
    }

    @GetMapping("/{id}")
    public ReservaDTO buscarPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id);
    }

    @PostMapping
    public ReservaDTO guardar(@RequestBody ReservaDTO dto) {
        return reservaService.guardar(dto);
    }

    @PutMapping("/{id}")
    public ReservaDTO actualizar(@PathVariable Long id, @RequestBody ReservaDTO dto) {
        return reservaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
    }
}