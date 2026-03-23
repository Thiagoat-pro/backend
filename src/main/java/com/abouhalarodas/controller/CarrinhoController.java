package com.abouhalarodas.controller;

import com.abouhalarodas.model.Carrinho;
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
    public Carrinho verCarrinho(@PathVariable Long clienteId) {
        return carrinhoService.verCarrinho(clienteId);
    }

    @PostMapping("/{clienteId}/itens")
    public Carrinho adicionarItem(
            @PathVariable Long clienteId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {
        return carrinhoService.adicionarItem(clienteId, produtoId, quantidade);
    }

    @DeleteMapping("/{clienteId}/itens/{itemId}")
    public Carrinho removerItem(
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
}