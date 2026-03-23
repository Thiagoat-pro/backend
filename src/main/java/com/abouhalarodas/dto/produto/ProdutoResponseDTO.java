package com.abouhalarodas.dto.produto;

import com.abouhalarodas.dto.categoria.CategoriaResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private BigDecimal precoPromocional;
    private Boolean emPromocao;
    private CategoriaResponseDTO categoria;
}