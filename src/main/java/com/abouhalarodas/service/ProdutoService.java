package com.abouhalarodas.service;

import com.abouhalarodas.model.Produto;
import com.abouhalarodas.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto save(Produto produto){

        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }

        if (produto.getPreco().doubleValue() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        if (produto.getEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }

        return repository.save(produto);
    }

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new RuntimeException("Produto não encontrado"));
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoque(produtoAtualizado.getEstoque());

        return repository.save(produto);
    }

    public void deletar(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }

        repository.deleteById(id);
    }

}