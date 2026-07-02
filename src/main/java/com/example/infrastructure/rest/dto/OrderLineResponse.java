package com.example.infrastructure.rest.dto;

import com.example.domain.OrderLine;
import java.math.BigDecimal;

public record OrderLineResponse(
        Long id,
        ProductResponse product,
        int quantity,
        BigDecimal unitPrice) {

    public static OrderLineResponse from(OrderLine line) {
        return new OrderLineResponse(
                line.getId(),
                ProductResponse.from(line.getProduct()),
                line.getQuantity(),
                line.getUnitPrice());
    }
}
