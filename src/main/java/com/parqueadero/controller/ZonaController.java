package com.parqueadero.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.dto.ZonaDTO;
import com.parqueadero.service.ZonaService;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    @GetMapping
    public List<ZonaDTO> listar() {
        return zonaService.listar();
    }

    @GetMapping("/{id}")
    public ZonaDTO buscarPorId(@PathVariable Long id) {
        return zonaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZonaDTO guardar(@RequestBody ZonaDTO dto) {
        return zonaService.guardar(dto);
    }

    @PutMapping("/{id}")
    public ZonaDTO actualizar(@PathVariable Long id, @RequestBody ZonaDTO dto) {
        return zonaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        zonaService.eliminar(id);
    }
}
