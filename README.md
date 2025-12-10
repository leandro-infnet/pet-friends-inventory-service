# 📦 Pet Friends - Inventory Service (Almoxarifado)

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-EDA-007396?style=for-the-badge&logo=apache-kafka&logoColor=white)

> **Microsserviço de Gestão de Estoque e Separação**
>
> Responsável pelo controle de inventário, reserva de SKUs e processamento de ordens de separação via eventos.

---

## 📖 Visão Geral

O **Almoxarifado Service** opera de forma reativa dentro do ecossistema Pet Friends. Ele não recebe chamadas síncronas de escrita para baixar estoque; em vez disso, ele reage a eventos de `PedidoCriado` para realizar a reserva e separação de produtos, garantindo desacoplamento do checkout.

### Fluxo de Integração (Event-Driven)

```mermaid
sequenceDiagram
    participant P as Pedidos Service
    participant Q as Message Broker
    participant L as Listener (Almoxarifado)
    participant D as Domain (ItemEstoque)
    participant DB as Database

    Note over P, Q: Evento com Payload Completo
    P->>Q: Publica PedidoCriadoEvent
    Q->>L: Consome Mensagem
    L->>L: Deserializa (Jackson)

    loop Para cada Item
        L->>D: carregarPorSKU()
        D->>D: reservar(quantidade)
        alt Estoque Insuficiente
            D-->>L: Erro de Domínio
            L-->>Q: Dead Letter Queue
        else Sucesso
            D->>DB: Atualiza Saldo
        end
    end
```

---

## 🏗️ Arquitetura de Domínio

O projeto segue princípios de DDD (Domain-Driven Design) para garantir a integridade do inventário.

* **Agregado Raiz**: `ItemEstoque`
* **Value Objects**: `SKU` (Identidade e validação de formato)
* **Comunicação**: Assíncrona via `Spring Events` (simulando RabbitMQ/Kafka).

**Estrutura de Pacotes**

```plaintext
br.com.petfriends.almoxarifado
├── application      # Casos de uso e Listeners
├── domain           # Regras de negócio (Puras e isoladas)
│   └── stock        # Agregado de Estoque
└── infra            # Configurações e Adaptadores
    ├── config       # Configuração Jackson/Messaging
    └── events       # DTOs de integração (Contratos)
```

---

## 🚀 Como Executar

**Pré-requisitos**

* JDK 21
* Maven 3.8+

**Rodando a Aplicação**

```bash
mvn spring-boot:run
```

**Testando a Ingestão de Eventos**

Como o serviço é passivo (Escuta eventos), verifique os logs para confirmar o processamento:

```plaintext
INFO ... PedidoEventListener : Novo evento recebido do Pedidos: PedidoID 12345
INFO ... PedidoEventListener : Separando SKU: PET-999 - Qtd: 2
```

**Pet Friends Engineering Team** © 2025
