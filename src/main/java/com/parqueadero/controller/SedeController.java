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

import com.parqueadero.dto.SedeDTO;
import com.parqueadero.service.SedeService;

@RestController
@RequestMapping("/api/sedes")
public class SedeController {

    private final SedeService sedeService;

    public SedeController(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    @GetMapping
    public List<SedeDTO> listar() {
        return sedeService.listar();
    }

    @GetMapping("/{id}")
    public SedeDTO buscarPorId(@PathVariable Long id) {
        return sedeService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SedeDTO guardar(@RequestBody SedeDTO dto) {
        return sedeService.guardar(dto);
    }

    @PutMapping("/{id}")
    public SedeDTO actualizar(@PathVariable Long id, @RequestBody SedeDTO dto) {
        return sedeService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        sedeService.eliminar(id);
    }
}
