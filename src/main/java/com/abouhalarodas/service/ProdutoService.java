package com.abouhalarodas.service;

import com.abouhalarodas.dto.categoria.CategoriaResponseDTO;
import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import com.abouhalarodas.model.Categoria;
import com.abouhalarodas.model.Produto;
import com.abouhalarodas.repository.CategoriaRepository;
import com.abouhalarodas.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public ProdutoResponseDTO save(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        if (produto.getPreco().doubleValue() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (produto.getEstoque() == null) {
            produto.setEstoque(0);
        }
        if (produto.getEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        if (produto.getEmPromocao() == null) {
            produto.setEmPromocao(false);
        }
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }
        return toDTO(repository.save(produto));
    }

    public List<ProdutoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO findById(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado")));
    }

    public ProdutoResponseDTO atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoque(produtoAtualizado.getEstoque());
        produto.setPrecoPromocional(produtoAtualizado.getPrecoPromocional());
        produto.setEmPromocao(produtoAtualizado.getEmPromocao() != null ? produtoAtualizado.getEmPromocao() : false);

        if (produtoAtualizado.getCategoria() != null && produtoAtualizado.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(produtoAtualizado.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }

        return toDTO(repository.save(produto));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }

    public Integer verEstoque(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return produto.getEstoque();
    }

    public List<ProdutoResponseDTO> buscar(String nome, Long categoriaId, Boolean emPromocao, BigDecimal precoMin, BigDecimal precoMax) {
        return repository.buscarComFiltros(nome, categoriaId, emPromocao, precoMin, precoMax)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ProdutoResponseDTO toDTO(Produto produto) {
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