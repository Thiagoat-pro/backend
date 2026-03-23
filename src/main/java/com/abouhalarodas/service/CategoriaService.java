package com.abouhalarodas.service;

import com.abouhalarodas.dto.categoria.CategoriaResponseDTO;
import com.abouhalarodas.model.Categoria;
import com.abouhalarodas.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public CategoriaResponseDTO salvar(Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio");
        }
        return toDTO(repository.save(categoria));
    }

    public List<CategoriaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada")));
    }

    public CategoriaResponseDTO atualizar(Long id, Categoria categoriaAtualizada) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setNome(categoriaAtualizada.getNome());
        return toDTO(repository.save(categoria));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada");
        }
        repository.deleteById(id);
    }

    private CategoriaResponseDTO toDTO(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        return dto;
    }
}