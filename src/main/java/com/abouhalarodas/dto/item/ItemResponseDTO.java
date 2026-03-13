package com.abouhalarodas.dto.item;

import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ItemResponseDTO {
    private Long id;
    private ProdutoResponseDTO produto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
}