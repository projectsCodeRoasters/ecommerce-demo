package com.example.domain;

// Value object: cantidad de unidades de una línea de pedido. Protege el
// invariante "una línea no puede pedir cero o menos unidades".
public record Quantity(int value) {

    public Quantity {
        if (value <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero: " + value);
        }
    }
}
