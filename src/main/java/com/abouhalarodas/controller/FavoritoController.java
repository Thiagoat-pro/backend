package com.abouhalarodas.controller;

import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import com.abouhalarodas.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/{clienteId}/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @PostMapping("/{produtoId}")
    public ProdutoResponseDTO favoritar(
            @PathVariable Long clienteId,
            @PathVariable Long produtoId) {
        return favoritoService.favoritar(clienteId, produtoId);
    }

    @DeleteMapping("/{produtoId}")
    public void desfavoritar(
            @PathVariable Long clienteId,
            @PathVariable Long produtoId) {
        favoritoService.desfavoritar(clienteId, produtoId);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listarFavoritos(@PathVariable Long clienteId) {
        return favoritoService.listarFavoritos(clienteId);
    }
}