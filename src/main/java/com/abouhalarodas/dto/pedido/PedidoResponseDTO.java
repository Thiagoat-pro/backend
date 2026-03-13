package com.abouhalarodas.dto.pedido;

import com.abouhalarodas.dto.cliente.ClienteResponseDTO;
import com.abouhalarodas.dto.item.ItemResponseDTO;
import com.abouhalarodas.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente;
    private List<ItemResponseDTO> itens;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal total;
}