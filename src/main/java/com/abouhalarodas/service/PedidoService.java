package com.abouhalarodas.service;

import com.abouhalarodas.model.Cliente;
import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.repository.ClienteRepository;
import com.abouhalarodas.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}