package com.parqueadero.service;

import java.util.List;

import com.parqueadero.dto.MenuRequest;
import com.parqueadero.dto.MenuResponse;
import com.parqueadero.model.Menu;

public interface MenuService {

    Menu crear(MenuRequest request);

    List<Menu> listarTodos();

    List<MenuResponse> obtenerArbolMenus();

    MenuResponse obtenerPorId(Long id);

    Menu actualizar(Long id, MenuRequest request);

    void eliminar(Long id);
}