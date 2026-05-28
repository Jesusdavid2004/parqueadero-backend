package com.parqueadero.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 200)
    private String ruta;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_id")
    @JsonBackReference
    private Menu padre;

    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Menu> hijos = new ArrayList<>();

    public Menu() {
    }

    public Menu(String nombre, String ruta, Boolean activo) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.activo = activo;
    }

    public void agregarHijo(Menu hijo) {
        hijo.setPadre(this);
        this.hijos.add(hijo);
    }

    public void removerHijo(Menu hijo) {
        hijo.setPadre(null);
        this.hijos.remove(hijo);
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

    public Menu getPadre() {
        return padre;
    }

    public List<Menu> getHijos() {
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

    public void setPadre(Menu padre) {
        this.padre = padre;
    }

    public void setHijos(List<Menu> hijos) {
        this.hijos = hijos;
    }
}