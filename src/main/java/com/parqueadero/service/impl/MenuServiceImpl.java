package com.parqueadero.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parqueadero.dto.MenuRequest;
import com.parqueadero.dto.MenuResponse;
import com.parqueadero.model.Menu;
import com.parqueadero.repository.MenuRepository;
import com.parqueadero.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu crear(MenuRequest request) {
        Menu menu = new Menu();
        menu.setNombre(request.getNombre());
        menu.setRuta(request.getRuta());
        menu.setActivo(request.getActivo() != null ? request.getActivo() : Boolean.TRUE);

        if (request.getPadreId() != null) {
            Menu padre = menuRepository.findById(request.getPadreId())
                    .orElseThrow(() -> new RuntimeException(
                            "No existe el menú padre con id: " + request.getPadreId()));
            menu.setPadre(padre);
        }

        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> listarTodos() {
        return menuRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> obtenerArbolMenus() {
        List<Menu> menusRaiz = menuRepository.findRaizConHijos();
        return convertirListaRecursiva(menusRaiz);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuResponse obtenerPorId(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú no encontrado con id: " + id));
        return convertirRecursivo(menu);
    }

    @Override
    public Menu actualizar(Long id, MenuRequest request) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú no encontrado con id: " + id));

        menu.setNombre(request.getNombre());
        menu.setRuta(request.getRuta());

        if (request.getActivo() != null) {
            menu.setActivo(request.getActivo());
        }

        if (request.getPadreId() != null) {
            if (request.getPadreId().equals(id)) {
                throw new RuntimeException("Un menú no puede ser padre de sí mismo");
            }

            Menu padre = menuRepository.findById(request.getPadreId())
                    .orElseThrow(() -> new RuntimeException(
                            "No existe el menú padre con id: " + request.getPadreId()));
            menu.setPadre(padre);
        } else {
            menu.setPadre(null);
        }

        return menuRepository.save(menu);
    }

    @Override
    public void eliminar(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú no encontrado con id: " + id));
        menuRepository.delete(menu);
    }

    private List<MenuResponse> convertirListaRecursiva(List<Menu> menus) {
        List<MenuResponse> respuesta = new ArrayList<>();

        for (Menu menu : menus) {
            respuesta.add(convertirRecursivo(menu));
        }

        return respuesta;
    }

    private MenuResponse convertirRecursivo(Menu menu) {
        MenuResponse dto = new MenuResponse();
        dto.setId(menu.getId());
        dto.setNombre(menu.getNombre());
        dto.setRuta(menu.getRuta());
        dto.setActivo(menu.getActivo());

        if (menu.getPadre() != null) {
            dto.setPadreId(menu.getPadre().getId());
        }

        List<MenuResponse> hijosDto = new ArrayList<>();

        if (menu.getHijos() != null) {
            for (Menu hijo : menu.getHijos()) {
                hijosDto.add(convertirRecursivo(hijo));
            }
        }

        dto.setHijos(hijosDto);
        return dto;
    }
}