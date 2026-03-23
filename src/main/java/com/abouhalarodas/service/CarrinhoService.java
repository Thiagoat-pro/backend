package com.abouhalarodas.service;

import com.abouhalarodas.dto.carrinho.CarrinhoResponseDTO;
import com.abouhalarodas.dto.carrinho.ItemCarrinhoResponseDTO;
import com.abouhalarodas.dto.categoria.CategoriaResponseDTO;
import com.abouhalarodas.dto.cliente.ClienteResponseDTO;
import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import com.abouhalarodas.model.*;
import com.abouhalarodas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public CarrinhoResponseDTO adicionarItem(Long clienteId, Long produtoId, Integer quantidade) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente. Disponível: " + produto.getEstoque());
        }

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

        return toDTO(carrinhoRepository.save(carrinho));
    }

    public CarrinhoResponseDTO removerItem(Long clienteId, Long itemId) {
        Carrinho carrinho = buscarOuCriarCarrinho(clienteId);
        carrinho.getItens().removeIf(i -> i.getId().equals(itemId));
        itemCarrinhoRepository.deleteById(itemId);
        return toDTO(carrinhoRepository.save(carrinho));
    }

    public CarrinhoResponseDTO verCarrinho(Long clienteId) {
        return toDTO(buscarOuCriarCarrinho(clienteId));
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

    private CarrinhoResponseDTO toDTO(Carrinho carrinho) {
        CarrinhoResponseDTO dto = new CarrinhoResponseDTO();
        dto.setId(carrinho.getId());

        ClienteResponseDTO clienteDTO = new ClienteResponseDTO();
        clienteDTO.setId(carrinho.getCliente().getId());
        clienteDTO.setNome(carrinho.getCliente().getNome());
        clienteDTO.setEmail(carrinho.getCliente().getEmail());
        clienteDTO.setTelefone(carrinho.getCliente().getTelefone());
        dto.setCliente(clienteDTO);

        List<ItemCarrinhoResponseDTO> itensDTO = carrinho.getItens().stream().map(item -> {
            ItemCarrinhoResponseDTO itemDTO = new ItemCarrinhoResponseDTO();
            itemDTO.setId(item.getId());
            itemDTO.setQuantidade(item.getQuantidade());

            ProdutoResponseDTO produtoDTO = new ProdutoResponseDTO();
            produtoDTO.setId(item.getProduto().getId());
            produtoDTO.setNome(item.getProduto().getNome());
            produtoDTO.setPreco(item.getProduto().getPreco());
            produtoDTO.setPrecoPromocional(item.getProduto().getPrecoPromocional());
            produtoDTO.setEmPromocao(item.getProduto().getEmPromocao());

            if (item.getProduto().getCategoria() != null) {
                CategoriaResponseDTO categoriaDTO = new CategoriaResponseDTO();
                categoriaDTO.setId(item.getProduto().getCategoria().getId());
                categoriaDTO.setNome(item.getProduto().getCategoria().getNome());
                produtoDTO.setCategoria(categoriaDTO);
            }

            itemDTO.setProduto(produtoDTO);
            return itemDTO;
        }).collect(Collectors.toList());

        dto.setItens(itensDTO);
        return dto;
    }
}