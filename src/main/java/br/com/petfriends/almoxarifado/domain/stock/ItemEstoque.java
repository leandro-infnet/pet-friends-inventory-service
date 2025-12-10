package br.com.petfriends.almoxarifado.domain.stock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ItemEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SKU sku;

    private int quantidadeDisponivel;
    private int quantidadeReservada;

    public ItemEstoque(SKU sku, int quantidadeInicial) {
        this.sku = sku;
        this.quantidadeDisponivel = quantidadeInicial;
        this.quantidadeReservada = 0;
    }

    public void reservar(int quantidade) {
        if (quantidade > this.quantidadeDisponivel) {
            throw new IllegalStateException("Estoque insuficiente para o SKU: " + sku.getCodigo());
        }
        this.quantidadeDisponivel -= quantidade;
        this.quantidadeReservada += quantidade;
    }
}
