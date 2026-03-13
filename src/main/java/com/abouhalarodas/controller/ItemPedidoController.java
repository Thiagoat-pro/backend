package com.abouhalarodas.controller;

import com.abouhalarodas.model.ItemPedido;
import com.abouhalarodas.service.ItemPedidoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    public ItemPedido criarItem(
            @RequestParam Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {

        return itemPedidoService.criarItem(pedidoId, produtoId, quantidade);
    }

    @GetMapping("/pedidos/{pedidoId}")
    public List<ItemPedido> listarItensPorPedido(@PathVariable Long pedidoId) {
        return itemPedidoService.listarItensPorPedido(pedidoId);
    }

    @DeleteMapping("/{itemId}")
    public void deletarItem(@PathVariable Long itemId) {
        itemPedidoService.deletarItem(itemId);
    }
}