package com.abouhalarodas.service;

import com.abouhalarodas.dto.cliente.ClienteResponseDTO;
import com.abouhalarodas.dto.endereco.EnderecoResponseDTO;
import com.abouhalarodas.dto.item.ItemResponseDTO;
import com.abouhalarodas.dto.pedido.PedidoResponseDTO;
import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import com.abouhalarodas.enums.StatusPedido;
import com.abouhalarodas.model.Cliente;
import com.abouhalarodas.model.Endereco;
import com.abouhalarodas.model.ItemPedido;
import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.repository.ClienteRepository;
import com.abouhalarodas.repository.EnderecoRepository;
import com.abouhalarodas.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

    public PedidoService(PedidoRepository repository, ClienteRepository clienteRepository, EnderecoRepository enderecoRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Pedido save(Pedido pedido){
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null){
            throw new IllegalArgumentException("Pedido precisa ter um cliente");
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        if (pedido.getEndereco() != null && pedido.getEndereco().getId() != null) {
            Endereco endereco = enderecoRepository.findById(pedido.getEndereco().getId())
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
            pedido.setEndereco(endereco);
        }

        return repository.save(pedido);
    }

    public List<PedidoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return toDTO(pedido);
    }

    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(status);
        return toDTO(repository.save(pedido));
    }

    private PedidoResponseDTO toDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus());
        dto.setTotal(pedido.getTotal());

        ClienteResponseDTO clienteDTO = new ClienteResponseDTO();
        clienteDTO.setId(pedido.getCliente().getId());
        clienteDTO.setNome(pedido.getCliente().getNome());
        clienteDTO.setEmail(pedido.getCliente().getEmail());
        clienteDTO.setTelefone(pedido.getCliente().getTelefone());
        dto.setCliente(clienteDTO);

        if (pedido.getEndereco() != null) {
            EnderecoResponseDTO enderecoDTO = new EnderecoResponseDTO();
            enderecoDTO.setId(pedido.getEndereco().getId());
            enderecoDTO.setCep(pedido.getEndereco().getCep());
            enderecoDTO.setRua(pedido.getEndereco().getRua());
            enderecoDTO.setNumero(pedido.getEndereco().getNumero());
            enderecoDTO.setComplemento(pedido.getEndereco().getComplemento());
            enderecoDTO.setBairro(pedido.getEndereco().getBairro());
            enderecoDTO.setCidade(pedido.getEndereco().getCidade());
            enderecoDTO.setEstado(pedido.getEndereco().getEstado());
            dto.setEndereco(enderecoDTO);
        }

        if (pedido.getItens() != null) {
            List<ItemResponseDTO> itensDTO = pedido.getItens().stream().map(this::itemToDTO).collect(Collectors.toList());
            dto.setItens(itensDTO);
        }

        return dto;
    }

    private ItemResponseDTO itemToDTO(ItemPedido item) {
        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(item.getId());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());

        ProdutoResponseDTO produtoDTO = new ProdutoResponseDTO();
        produtoDTO.setId(item.getProduto().getId());
        produtoDTO.setNome(item.getProduto().getNome());
        produtoDTO.setPreco(item.getProduto().getPreco());
        dto.setProduto(produtoDTO);

        return dto;
    }
}