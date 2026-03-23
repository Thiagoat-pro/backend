package com.abouhalarodas.dto.relatorio;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RelatorioResponseDTO {
    private BigDecimal totalVendas;
    private Long totalPedidos;
    private Long totalClientes;
    private Map<String, Integer> produtosMaisVendidos;
}