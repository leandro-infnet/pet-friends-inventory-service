package br.com.petfriends.almoxarifado.domain.stock;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class SKU implements Serializable {

    private String codigo;

    protected SKU() {}

    public SKU(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU não pode ser vazio");
        }

        if (!codigo.matches("[A-Z]{3}-\\d{4}")) {
            throw new IllegalArgumentException("Formato de SKU inválido. Esperado: XXX-0000");
        }
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKU sku = (SKU) o;
        return Objects.equals(codigo, sku.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
