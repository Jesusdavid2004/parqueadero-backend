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

import com.parqueadero.dto.TarifaDTO;
import com.parqueadero.service.TarifaService;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping
    public List<TarifaDTO> listar() {
        return tarifaService.listar();
    }

    @GetMapping("/{id}")
    public TarifaDTO buscarPorId(@PathVariable Long id) {
        return tarifaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarifaDTO guardar(@RequestBody TarifaDTO dto) {
        return tarifaService.guardar(dto);
    }

    @PutMapping("/{id}")
    public TarifaDTO actualizar(@PathVariable Long id, @RequestBody TarifaDTO dto) {
        return tarifaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        tarifaService.eliminar(id);
    }
}
