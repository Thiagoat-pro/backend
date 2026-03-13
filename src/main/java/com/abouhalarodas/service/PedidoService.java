package com.abouhalarodas.service;

import com.abouhalarodas.dto.cliente.ClienteResponseDTO;
import com.abouhalarodas.dto.item.ItemResponseDTO;
import com.abouhalarodas.dto.pedido.PedidoResponseDTO;
import com.abouhalarodas.dto.produto.ProdutoResponseDTO;
import com.abouhalarodas.model.Cliente;
import com.abouhalarodas.model.ItemPedido;
import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.repository.ClienteRepository;
import com.abouhalarodas.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public Pedido save(Pedido pedido){
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null){
            throw new IllegalArgumentException("Pedido precisa ter um cliente");
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
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

    public void deletar(Long id) {
        repository.deleteById(id);
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