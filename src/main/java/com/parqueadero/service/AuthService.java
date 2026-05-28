package com.parqueadero.service;

import com.parqueadero.dto.AuthResponse;
import com.parqueadero.dto.LoginRequest;
import com.parqueadero.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse me(String username);
}