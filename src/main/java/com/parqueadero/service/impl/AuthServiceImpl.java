package com.parqueadero.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parqueadero.dto.AuthResponse;
import com.parqueadero.dto.LoginRequest;
import com.parqueadero.dto.RegisterRequest;
import com.parqueadero.enums.RolUsuario;
import com.parqueadero.exception.BusinessException;
import com.parqueadero.exception.ResourceNotFoundException;
import com.parqueadero.model.Cliente;
import com.parqueadero.model.Usuario;
import com.parqueadero.repository.ClienteRepository;
import com.parqueadero.repository.UsuarioRepository;
import com.parqueadero.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new BusinessException("El username es obligatorio");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new BusinessException("El email es obligatorio");
        }

        if (request.getPassword() == null || request.getPassword().length() < 4) {
            throw new BusinessException("La contraseña debe tener al menos 4 caracteres");
        }

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("El username ya existe");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya existe");
        }

        RolUsuario rol = request.getRol() != null ? request.getRol() : RolUsuario.CLIENTE;

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername().trim());
        usuario.setEmail(request.getEmail().trim());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);

        if (rol == RolUsuario.CLIENTE && request.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cliente no encontrado con id " + request.getClienteId()));
            usuario.setCliente(cliente);
        } else {
            usuario.setCliente(null);
        }

        Usuario guardado = usuarioRepository.save(usuario);

        return new AuthResponse(
                guardado.getId(),
                guardado.getUsername(),
                guardado.getEmail(),
                guardado.getRol(),
                guardado.getCliente() != null ? guardado.getCliente().getId() : null,
                "Usuario registrado correctamente"
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new BusinessException("El username es obligatorio");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new BusinessException("La contraseña es obligatoria");
        }

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BusinessException("Credenciales inválidas");
        }

        return new AuthResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getCliente() != null ? usuario.getCliente().getId() : null,
                "Login exitoso"
        );
    }

    @Override
    public AuthResponse me(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new AuthResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getCliente() != null ? usuario.getCliente().getId() : null,
                "Usuario autenticado"
        );
    }
}