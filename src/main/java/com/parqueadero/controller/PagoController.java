package com.parqueadero.controller;

import com.parqueadero.dto.PagoDTO;
import com.parqueadero.service.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin("*")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoDTO> listar() {
        return pagoService.listar();
    }

    @GetMapping("/{id}")
    public PagoDTO buscarPorId(@PathVariable Long id) {
        return pagoService.buscarPorId(id);
    }

    @PostMapping
    public PagoDTO guardar(@RequestBody PagoDTO dto) {
        return pagoService.guardar(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
    }
}