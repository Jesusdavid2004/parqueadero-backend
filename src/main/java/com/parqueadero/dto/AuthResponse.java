package com.parqueadero.dto;

import com.parqueadero.enums.RolUsuario;

public class AuthResponse {

    private Long id;
    private String username;
    private String email;
    private RolUsuario rol;
    private Long clienteId;
    private String mensaje;

    public AuthResponse() {
    }

    public AuthResponse(Long id, String username, String email, RolUsuario rol, Long clienteId, String mensaje) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
        this.clienteId = clienteId;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}