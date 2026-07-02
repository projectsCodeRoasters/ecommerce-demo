package com.example.infrastructure.rest.dto;

import java.util.List;

public record PlaceOrderRequest(Long customerId, List<OrderLineRequest> lines) {

    public record OrderLineRequest(Long productId, int quantity) {
    }
}
