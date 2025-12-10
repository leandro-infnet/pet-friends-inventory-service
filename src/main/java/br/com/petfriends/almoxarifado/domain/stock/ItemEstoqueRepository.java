package br.com.petfriends.almoxarifado.domain.stock;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findBySku(SKU sku);
}
