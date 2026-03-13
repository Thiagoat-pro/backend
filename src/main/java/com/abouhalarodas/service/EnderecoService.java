package com.abouhalarodas.service;

import com.abouhalarodas.dto.endereco.EnderecoResponseDTO;
import com.abouhalarodas.model.Cliente;
import com.abouhalarodas.model.Endereco;
import com.abouhalarodas.repository.ClienteRepository;
import com.abouhalarodas.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    public EnderecoResponseDTO salvar(Long clienteId, Endereco endereco) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        endereco.setCliente(cliente);
        return toDTO(enderecoRepository.save(endereco));
    }

    public List<EnderecoResponseDTO> listarPorCliente(Long clienteId) {
        return enderecoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EnderecoResponseDTO buscarPorId(Long id) {
        return toDTO(enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado")));
    }

    public EnderecoResponseDTO atualizar(Long id, Endereco enderecoAtualizado) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        endereco.setCep(enderecoAtualizado.getCep());
        endereco.setRua(enderecoAtualizado.getRua());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setComplemento(enderecoAtualizado.getComplemento());
        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        return toDTO(enderecoRepository.save(endereco));
    }

    public void deletar(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }

    private EnderecoResponseDTO toDTO(Endereco endereco) {
        EnderecoResponseDTO dto = new EnderecoResponseDTO();
        dto.setId(endereco.getId());
        dto.setCep(endereco.getCep());
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setComplemento(endereco.getComplemento());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        return dto;
    }
}