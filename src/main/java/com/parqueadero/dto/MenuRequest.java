package com.parqueadero.dto;

public class MenuRequest {

    private String nombre;
    private String ruta;
    private Boolean activo;
    private Long padreId;

    public MenuRequest() {
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
}