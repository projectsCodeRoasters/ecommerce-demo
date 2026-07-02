package com.example;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Lógica de negocio en el controller (validación de stock, cálculo del
// total) y entidad JPA devuelta directamente al cliente — a propósito, es
// lo que el ejercicio de arquitectura hexagonal debe corregir.
@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public record OrderLineRequest(Long productId, int quantity) {
    }

    public record PlaceOrderRequest(Long customerId, List<OrderLineRequest> lines) {
    }

    @PostMapping("/orders")
    public Order placeOrder(@RequestBody PlaceOrderRequest request) {
        Customer customer = customerRepository.findById(request.customerId()).get();
        Order order = new Order(customer);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLineRequest lineRequest : request.lines()) {
            Product product = productRepository.findById(lineRequest.productId()).get();
            if (product.getStock() < lineRequest.quantity()) {
                throw new RuntimeException("Not enough stock for " + product.getName());
            }
            OrderLine line = new OrderLine(product, lineRequest.quantity(), product.getPrice());
            order.addLine(line);
            total = total.add(line.lineTotal());
        }
        order.setTotal(total);
        order.setStatus("CONFIRMED");

        return orderRepository.save(order);
    }
}
