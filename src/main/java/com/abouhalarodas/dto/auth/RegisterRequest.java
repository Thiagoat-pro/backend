package com.abouhalarodas.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;
}