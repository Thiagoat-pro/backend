package com.abouhalarodas.dto.endereco;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponseDTO {
    private Long id;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
}