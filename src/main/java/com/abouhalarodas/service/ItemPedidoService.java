package com.abouhalarodas.service;

import com.abouhalarodas.model.ItemPedido;
import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.model.Produto;
import com.abouhalarodas.repository.ItemPedidoRepository;
import com.abouhalarodas.repository.PedidoRepository;
import com.abouhalarodas.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if (produto.getEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente. Disponível: " + produto.getEstoque());
        }

        produto.setEstoque(produto.getEstoque() - quantidade);
        produtoRepository.save(produto);

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());

        return itemPedidoRepository.save(item);
    }

    public List<ItemPedido> listarItensPorPedido(Long pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }

    public void deletarItem(Long itemId) {
        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() + item.getQuantidade());
        produtoRepository.save(produto);

        itemPedidoRepository.deleteById(itemId);
    }
}