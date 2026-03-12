package com.abouhalarodas.controller;

import com.abouhalarodas.model.ItemPedido;
import com.abouhalarodas.service.ItemPedidoService;
import org.springframework.web.bind.annotation.*;

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
}