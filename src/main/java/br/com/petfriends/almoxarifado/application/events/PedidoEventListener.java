package br.com.petfriends.almoxarifado.application.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.petfriends.almoxarifado.infra.events.PedidoCriadoEvent;

@Component
public class PedidoEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PedidoEventListener.class);

    @EventListener
    public void handle(PedidoCriadoEvent event) {
        logger.info("Novo evento recebido do Pedidos: PedidoID {}", event.pedidoId());

        event.itens().forEach(item ->
            logger.info("Separando SKU: {} - Qtd: {}", item.sku(), item.quantidade())
        );
    }
}
