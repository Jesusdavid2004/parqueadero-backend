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

import com.parqueadero.dto.EmpleadoDTO;
import com.parqueadero.service.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<EmpleadoDTO> listar() {
        return empleadoService.listar();
    }

    @GetMapping("/{id}")
    public EmpleadoDTO buscarPorId(@PathVariable Long id) {
        return empleadoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoDTO guardar(@RequestBody EmpleadoDTO dto) {
        return empleadoService.guardar(dto);
    }

    @PutMapping("/{id}")
    public EmpleadoDTO actualizar(@PathVariable Long id, @RequestBody EmpleadoDTO dto) {
        return empleadoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
    }
}
