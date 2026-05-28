package com.parqueadero.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parqueadero.dto.MenuRequest;
import com.parqueadero.dto.MenuResponse;
import com.parqueadero.model.Menu;
import com.parqueadero.service.MenuService;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<Menu> crear(@RequestBody MenuRequest request) {
        return ResponseEntity.ok(menuService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<Menu>> listarTodos() {
        return ResponseEntity.ok(menuService.listarTodos());
    }

    @GetMapping("/arbol")
    public ResponseEntity<List<MenuResponse>> obtenerArbol() {
        return ResponseEntity.ok(menuService.obtenerArbolMenus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> actualizar(@PathVariable Long id, @RequestBody MenuRequest request) {
        return ResponseEntity.ok(menuService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        menuService.eliminar(id);
        return ResponseEntity.ok("Menú eliminado correctamente");
    }
}