package model;

import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Produto {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    BigDecimal preco;
    Integer estoque;


}
