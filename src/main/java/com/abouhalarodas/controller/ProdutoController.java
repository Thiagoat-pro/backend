package com.abouhalarodas.controller;

import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import com.abouhalarodas.model.Produto;
import com.abouhalarodas.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ProdutoResponseDTO salvar(@RequestBody Produto produto) {
        return service.save(produto);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/buscar")
    public List<ProdutoResponseDTO> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Boolean emPromocao,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax) {
        return service.buscar(nome, categoriaId, emPromocao, precoMin, precoMax);
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/estoque")
    public Integer verEstoque(@PathVariable Long id) {
        return service.verEstoque(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        return service.atualizarProduto(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}