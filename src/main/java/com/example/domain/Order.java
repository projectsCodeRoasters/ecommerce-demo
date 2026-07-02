package com.example.domain;

import com.example.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Agregado raíz: única puerta de entrada para modificar sus líneas y
// mantener el invariante "el total es siempre la suma de las líneas".
// Modelo de dominio puro: sin JPA, sin Spring. La persistencia se resuelve
// en infrastructure/persistence a través del puerto OrderRepository.
public class Order {

    private OrderId id;
    private final Customer customer;
    private final List<OrderLine> lines = new ArrayList<>();
    private Money total;
    private String status;

    public Order(Customer customer) {
        this.customer = customer;
        this.status = "CREATED";
    }

    public Order(OrderId id, Customer customer, List<OrderLine> lines, Money total, String status) {
        this.id = id;
        this.customer = customer;
        this.lines.addAll(lines);
        this.total = total;
        this.status = status;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public Money calculateTotal() {
        return lines.stream()
                .map(OrderLine::lineTotal)
                .reduce(Money.zero(), Money::add);
    }

    public void confirm() {
        this.total = calculateTotal();
        this.status = "CONFIRMED";
    }

    // Asigna la identidad tras persistir por primera vez — un Order recién
    // creado nace sin OrderId (ver getId()).
    public void assignId(OrderId id) {
        this.id = id;
    }

    public Optional<OrderId> getId() {
        return Optional.ofNullable(id);
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public Money getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
