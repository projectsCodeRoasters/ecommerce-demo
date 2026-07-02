package com.example;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Mezcla lógica de negocio con acceso a datos y validación de stock — a
// propósito, es lo que el ejercicio de arquitectura hexagonal debe corregir.
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // TODO: aún no implementado — punto de partida del ejercicio de TDD.
    // Debe sumar el total de todas las líneas del pedido.
    public BigDecimal calculateTotal(Order order) {
        return null;
    }

    public Order placeOrder(Order order) {
        for (OrderLine line : order.getLines()) {
            Product product = productRepository.findById(line.getProduct().getId()).get();
            if (product.getStock() < line.getQuantity()) {
                throw new RuntimeException("Not enough stock for " + product.getName());
            }
            product.setStock(product.getStock() - line.getQuantity());
            productRepository.save(product);
        }
        order.setTotal(calculateTotal(order));
        order.setStatus("CONFIRMED");
        return orderRepository.save(order);
    }
}
