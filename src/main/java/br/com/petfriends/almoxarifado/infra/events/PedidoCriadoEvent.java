package br.com.petfriends.almoxarifado.infra.events;

import java.util.List;

public record PedidoCriadoEvent(
    String pedidoId,
    List<ItemPedidoDTO> itens
) {
    public record ItemPedidoDTO(
        String sku,
        Integer quantidade
    ) {}
}
