package com.abouhalarodas.controller;

import com.abouhalarodas.dto.auth.AuthResponse;
import com.abouhalarodas.dto.auth.LoginRequest;
import com.abouhalarodas.dto.auth.RegisterRequest;
import com.abouhalarodas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrar")
    public AuthResponse registrar(@RequestBody RegisterRequest request) {
        return authService.registrar(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}