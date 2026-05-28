package com.parqueadero.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.dto.EspacioParqueoDTO;
import com.parqueadero.service.EspacioParqueoService;

@RestController
@RequestMapping("/api/espacios")
@CrossOrigin("*")
public class EspacioParqueoController {

    private final EspacioParqueoService espacioParqueoService;

    public EspacioParqueoController(EspacioParqueoService espacioParqueoService) {
        this.espacioParqueoService = espacioParqueoService;
    }

    @GetMapping("/disponibles")
    public List<EspacioParqueoDTO> listarDisponibles() {
        return espacioParqueoService.listarDisponibles();
    }
}