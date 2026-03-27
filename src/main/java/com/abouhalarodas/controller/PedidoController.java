package com.abouhalarodas.controller;

import com.abouhalarodas.dto.pedido.PedidoResponseDTO;
import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public Pedido salvar(@RequestBody Pedido pedido) {
        return service.save(pedido);
    }

    @GetMapping
    public List<PedidoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<PedidoResponseDTO> listarPorCliente(@PathVariable Long clienteId) {
        return service.listarPorCliente(clienteId);
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}