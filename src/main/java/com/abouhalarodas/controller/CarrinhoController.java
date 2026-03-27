package com.abouhalarodas.controller;

import com.abouhalarodas.dto.carrinho.CarrinhoResponseDTO;
import com.abouhalarodas.dto.pedido.PedidoResponseDTO;
import com.abouhalarodas.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @GetMapping("/{clienteId}")
    public CarrinhoResponseDTO verCarrinho(@PathVariable Long clienteId) {
        return carrinhoService.verCarrinho(clienteId);
    }

    @PostMapping("/{clienteId}/itens")
    public CarrinhoResponseDTO adicionarItem(
            @PathVariable Long clienteId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {
        return carrinhoService.adicionarItem(clienteId, produtoId, quantidade);
    }

    @DeleteMapping("/{clienteId}/itens/{itemId}")
    public CarrinhoResponseDTO removerItem(
            @PathVariable Long clienteId,
            @PathVariable Long itemId) {
        return carrinhoService.removerItem(clienteId, itemId);
    }

    @GetMapping("/{clienteId}/total")
    public BigDecimal calcularTotal(@PathVariable Long clienteId) {
        return carrinhoService.calcularTotal(clienteId);
    }

    @DeleteMapping("/{clienteId}/limpar")
    public void limparCarrinho(@PathVariable Long clienteId) {
        carrinhoService.limparCarrinho(clienteId);
    }

    @PostMapping("/{clienteId}/finalizar")
    public PedidoResponseDTO finalizar(
            @PathVariable Long clienteId,
            @RequestParam Long enderecoId) {
        return carrinhoService.finalizarCarrinho(clienteId, enderecoId);
    }
}