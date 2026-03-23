package com.abouhalarodas.service;

import com.abouhalarodas.dto.relatorio.RelatorioResponseDTO;
import com.abouhalarodas.model.ItemPedido;
import com.abouhalarodas.model.Pedido;
import com.abouhalarodas.repository.ClienteRepository;
import com.abouhalarodas.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public RelatorioResponseDTO gerarRelatorio() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        BigDecimal totalVendas = pedidos.stream()
                .map(Pedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Integer> produtosMaisVendidos = new HashMap<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getItens() != null) {
                for (ItemPedido item : pedido.getItens()) {
                    String nomeProduto = item.getProduto().getNome();
                    produtosMaisVendidos.merge(nomeProduto, item.getQuantidade(), Integer::sum);
                }
            }
        }

        RelatorioResponseDTO dto = new RelatorioResponseDTO();
        dto.setTotalVendas(totalVendas);
        dto.setTotalPedidos((long) pedidos.size());
        dto.setTotalClientes(clienteRepository.count());
        dto.setProdutosMaisVendidos(produtosMaisVendidos);

        return dto;
    }
}