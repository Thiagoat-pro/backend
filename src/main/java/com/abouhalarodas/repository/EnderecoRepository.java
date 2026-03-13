package com.abouhalarodas.repository;

import com.abouhalarodas.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByClienteId(Long clienteId);
}