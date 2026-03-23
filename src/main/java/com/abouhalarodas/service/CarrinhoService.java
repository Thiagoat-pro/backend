package com.abouhalarodas.service;

import com.abouhalarodas.model.*;
import com.abouhalarodas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;

    public Carrinho buscarOuCriarCarrinho(Long clienteId) {
        return carrinhoRepository.findByClienteId(clienteId).orElseGet(() -> {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            Carrinho carrinho = new Carrinho();
            carrinho.setCliente(cliente);
            carrinho.setItens(new ArrayList<>());
            return carrinhoRepository.save(carrinho);
        });
    }

    public Carrinho adicionarItem(Long clienteId, Long produtoId, Integer quantidade) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente. Disponível: " + produto.getEstoque());
        }

        // verifica se o produto já está no carrinho
        ItemCarrinho itemExistente = carrinho.getItens().stream()
                .filter(i -> i.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
            itemCarrinhoRepository.save(itemExistente);
        } else {
            ItemCarrinho item = new ItemCarrinho();
            item.setCarrinho(carrinho);
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            itemCarrinhoRepository.save(item);
            carrinho.getItens().add(item);
        }

        return carrinhoRepository.save(carrinho);
    }

    public Carrinho removerItem(Long clienteId, Long itemId) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);
        carrinho.getItens().removeIf(i -> i.getId().equals(itemId));
        itemCarrinhoRepository.deleteById(itemId);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho verCarrinho(Long clienteId) {
        return buscarOuCriarCarrinho(clienteId);
    }

    public BigDecimal calcularTotal(Long clienteId) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);
        return carrinho.getItens().stream()
                .map(i -> i.getProduto().getPreco().multiply(new BigDecimal(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void limparCarrinho(Long clienteId) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);
        carrinho.getItens().clear();
        carrinhoRepository.save(carrinho);
    }
}