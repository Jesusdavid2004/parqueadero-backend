package com.parqueadero.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuResponse {

    private Long id;
    private String nombre;
    private String ruta;
    private Boolean activo;
    private Long padreId;
    private List<MenuResponse> hijos = new ArrayList<>();

    public MenuResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Long getPadreId() {
        return padreId;
    }

    public List<MenuResponse> getHijos() {
        return hijos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setPadreId(Long padreId) {
        this.padreId = padreId;
    }

    public void setHijos(List<MenuResponse> hijos) {
        this.hijos = hijos;
    }
}