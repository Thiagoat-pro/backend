package com.abouhalarodas.dto.carrinho;

import com.abouhalarodas.dto.cliente.ClienteResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarrinhoResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente;
    private List<com.abouhalarodas.dto.carrinho.ItemCarrinhoResponseDTO> itens;
}