package com.abouhalarodas.repository;

import com.abouhalarodas.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByClienteId(Long clienteId);
    Optional<Favorito> findByClienteIdAndProdutoId(Long clienteId, Long produtoId);
}