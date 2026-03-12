package com.abouhalarodas.service;

import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public Pedido save(Pedido pedido){

        if (pedido.getCliente() == null || pedido.getCliente().getId() == null){
            throw new IllegalArgumentException("Pedido precisa ter um cliente");
        }

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