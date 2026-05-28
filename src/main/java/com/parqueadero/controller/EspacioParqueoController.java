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

import com.parqueadero.dto.EspacioParqueoDTO;
import com.parqueadero.service.EspacioParqueoService;

@RestController
@RequestMapping("/api/espacios")
public class EspacioParqueoController {

    private final EspacioParqueoService espacioParqueoService;

    public EspacioParqueoController(EspacioParqueoService espacioParqueoService) {
        this.espacioParqueoService = espacioParqueoService;
    }

    @GetMapping
    public List<EspacioParqueoDTO> listar() {
        return espacioParqueoService.listar();
    }

    @GetMapping("/disponibles")
    public List<EspacioParqueoDTO> listarDisponibles() {
        return espacioParqueoService.listarDisponibles();
    }

    @GetMapping("/{id}")
    public EspacioParqueoDTO buscarPorId(@PathVariable Long id) {
        return espacioParqueoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EspacioParqueoDTO guardar(@RequestBody EspacioParqueoDTO dto) {
        return espacioParqueoService.guardar(dto);
    }

    @PutMapping("/{id}")
    public EspacioParqueoDTO actualizar(@PathVariable Long id, @RequestBody EspacioParqueoDTO dto) {
        return espacioParqueoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        espacioParqueoService.eliminar(id);
    }
}