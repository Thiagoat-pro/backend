package com.abouhalarodas.dto.produto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
}