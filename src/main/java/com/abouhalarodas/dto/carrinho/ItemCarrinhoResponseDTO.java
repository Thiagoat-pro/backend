package com.abouhalarodas.dto.carrinho;

import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCarrinhoResponseDTO {
    private Long id;
    private ProdutoResponseDTO produto;
    private Integer quantidade;
}