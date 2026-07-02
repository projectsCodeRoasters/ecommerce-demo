package com.example.application;

import com.example.domain.Order;

// Puerto de entrada: único punto de contacto que conoce el adaptador REST.
public interface PlaceOrderUseCase {

    Order placeOrder(PlaceOrderCommand command);
}
