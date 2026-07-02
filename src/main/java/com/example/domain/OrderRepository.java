package com.example.domain;

// Puerto de salida: lo implementa el adaptador JPA en infrastructure/persistence.
public interface OrderRepository {

    Order save(Order order);
}
