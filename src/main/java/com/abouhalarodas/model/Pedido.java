package com.abouhalarodas.model;

import com.abouhalarodas.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Transient
    public BigDecimal getTotal() {
        if (itens == null || itens.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return itens.stream()
                .map(item -> item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}