package com.example.infrastructure.rest.dto;

import com.example.domain.Order;
import java.math.BigDecimal;
import java.util.List;

// Nunca se devuelve la entidad/objeto de dominio Order directamente al
// cliente HTTP — este DTO reproduce la misma forma de JSON que antes
// exponía la entidad JPA Order.
public record OrderResponse(
        Long id,
        CustomerResponse customer,
        List<OrderLineResponse> lines,
        BigDecimal total,
        String status) {

    public static OrderResponse from(Order order) {
        List<OrderLineResponse> lines = order.getLines().stream()
                .map(OrderLineResponse::from)
                .toList();
        return new OrderResponse(
                order.getId(),
                CustomerResponse.from(order.getCustomer()),
                lines,
                order.getTotal(),
                order.getStatus());
    }
}
