package com.abouhalarodas.service;

import com.abouhalarodas.model.ItemPedido;
import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.model.Produto;
import com.abouhalarodas.repository.ItemPedidoRepository;
import com.abouhalarodas.repository.PedidoRepository;
import com.abouhalarodas.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(
            ItemPedidoRepository itemPedidoRepository,
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository) {

        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public ItemPedido criarItem(Long pedidoId, Long produtoId, Integer quantidade) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedido item = new ItemPedido();

        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());

        return itemPedidoRepository.save(item);
    }
}