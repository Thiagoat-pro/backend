package com.abouhalarodas.controller;

import com.abouhalarodas.dto.categoria.CategoriaResponseDTO;
import com.abouhalarodas.model.Categoria;
import com.abouhalarodas.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping
    public CategoriaResponseDTO salvar(@RequestBody Categoria categoria) {
        return service.salvar(categoria);
    }

    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        return service.atualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}