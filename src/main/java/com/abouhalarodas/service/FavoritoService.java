package com.abouhalarodas.service;

import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import com.abouhalarodas.dto.categoria.CategoriaResponseDTO;
import com.abouhalarodas.model.Cliente;
import com.abouhalarodas.model.Favorito;
import com.abouhalarodas.model.Produto;
import com.abouhalarodas.repository.ClienteRepository;
import com.abouhalarodas.repository.FavoritoRepository;
import com.abouhalarodas.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO favoritar(Long clienteId, Long produtoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        favoritoRepository.findByClienteIdAndProdutoId(clienteId, produtoId)
                .ifPresent(f -> { throw new RuntimeException("Produto já está nos favoritos"); });

        Favorito favorito = new Favorito();
        favorito.setCliente(cliente);
        favorito.setProduto(produto);
        favoritoRepository.save(favorito);

        return toProdutoDTO(produto);
    }

    public void desfavoritar(Long clienteId, Long produtoId) {
        Favorito favorito = favoritoRepository.findByClienteIdAndProdutoId(clienteId, produtoId)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));
        favoritoRepository.delete(favorito);
    }

    public List<ProdutoResponseDTO> listarFavoritos(Long clienteId) {
        return favoritoRepository.findByClienteId(clienteId)
                .stream()
                .map(f -> toProdutoDTO(f.getProduto()))
                .collect(Collectors.toList());
    }

    private ProdutoResponseDTO toProdutoDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setPrecoPromocional(produto.getPrecoPromocional());
        dto.setEmPromocao(produto.getEmPromocao());

        if (produto.getCategoria() != null) {
            CategoriaResponseDTO categoriaDTO = new CategoriaResponseDTO();
            categoriaDTO.setId(produto.getCategoria().getId());
            categoriaDTO.setNome(produto.getCategoria().getNome());
            dto.setCategoria(categoriaDTO);
        }

        return dto;
    }
}