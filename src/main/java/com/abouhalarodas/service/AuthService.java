package com.abouhalarodas.service;

import com.abouhalarodas.config.JwtService;
import com.abouhalarodas.dto.auth.AuthResponse;
import com.abouhalarodas.dto.auth.LoginRequest;
import com.abouhalarodas.dto.auth.RegisterRequest;
import com.abouhalarodas.enums.Role;
import com.abouhalarodas.model.Usuario;
import com.abouhalarodas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegisterRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(Role.CLIENTE);

        usuarioRepository.save(usuario);

        return new AuthResponse(jwtService.gerarToken(usuario));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new AuthResponse(jwtService.gerarToken(usuario));
    }
}