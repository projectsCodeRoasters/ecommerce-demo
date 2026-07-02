package com.example.domain;

import java.util.Objects;

// Identidad del agregado raíz Order. Un pedido recién creado aún no tiene
// OrderId (se asigna al persistir) — por eso Order expone Optional<OrderId>
// en vez de permitir un id nulo.
public record OrderId(Long value) {

    public OrderId {
        Objects.requireNonNull(value, "OrderId no puede ser null");
    }
}
