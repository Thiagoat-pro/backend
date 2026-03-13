package com.abouhalarodas.controller;

import com.abouhalarodas.dto.endereco.EnderecoResponseDTO;
import com.abouhalarodas.model.Endereco;
import com.abouhalarodas.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/{clienteId}/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    public EnderecoResponseDTO salvar(@PathVariable Long clienteId, @RequestBody Endereco endereco) {
        return enderecoService.salvar(clienteId, endereco);
    }

    @GetMapping
    public List<EnderecoResponseDTO> listar(@PathVariable Long clienteId) {
        return enderecoService.listarPorCliente(clienteId);
    }

    @GetMapping("/{id}")
    public EnderecoResponseDTO buscarPorId(@PathVariable Long clienteId, @PathVariable Long id) {
        return enderecoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EnderecoResponseDTO atualizar(@PathVariable Long clienteId, @PathVariable Long id, @RequestBody Endereco endereco) {
        return enderecoService.atualizar(id, endereco);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long clienteId, @PathVariable Long id) {
        enderecoService.deletar(id);
    }
}