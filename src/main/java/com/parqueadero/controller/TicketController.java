package com.parqueadero.controller;

import com.parqueadero.dto.TicketDTO;
import com.parqueadero.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketDTO> listar() {
        return ticketService.listar();
    }

    @GetMapping("/{id}")
    public TicketDTO buscarPorId(@PathVariable Long id) {
        return ticketService.buscarPorId(id);
    }

    @PostMapping
    public TicketDTO guardar(@RequestBody TicketDTO dto) {
        return ticketService.guardar(dto);
    }

    @PutMapping("/{id}")
    public TicketDTO actualizar(@PathVariable Long id, @RequestBody TicketDTO dto) {
        return ticketService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ticketService.eliminar(id);
    }
}