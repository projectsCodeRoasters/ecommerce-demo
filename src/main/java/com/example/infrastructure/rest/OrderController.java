package com.example.infrastructure.rest;

import com.example.application.PlaceOrderCommand;
import com.example.application.PlaceOrderCommand.OrderLineCommand;
import com.example.application.PlaceOrderUseCase;
import com.example.domain.Order;
import com.example.infrastructure.rest.dto.OrderResponse;
import com.example.infrastructure.rest.dto.PlaceOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private PlaceOrderUseCase placeOrderUseCase;

    @PostMapping("/orders")
    public OrderResponse placeOrder(@RequestBody PlaceOrderRequest request) {
        PlaceOrderCommand command = toCommand(request);
        Order order = placeOrderUseCase.placeOrder(command);
        return OrderResponse.from(order);
    }

    private PlaceOrderCommand toCommand(PlaceOrderRequest request) {
        var lines = request.lines().stream()
                .map(line -> new OrderLineCommand(line.productId(), line.quantity()))
                .toList();
        return new PlaceOrderCommand(request.customerId(), lines);
    }
}
