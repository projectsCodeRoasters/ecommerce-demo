package com.example.application;

import java.util.List;

// Comando de entrada al caso de uso — no es una entidad JPA ni un DTO de HTTP.
public record PlaceOrderCommand(Long customerId, List<OrderLineCommand> lines) {

    public record OrderLineCommand(Long productId, int quantity) {
    }
}
