package com.example.domain;

import com.example.Customer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Modelo de dominio puro: sin JPA, sin Spring. La persistencia se resuelve
// en infrastructure/persistence a través del puerto OrderRepository.
public class Order {

    private Long id;
    private final Customer customer;
    private final List<OrderLine> lines = new ArrayList<>();
    private BigDecimal total;
    private String status;

    public Order(Customer customer) {
        this.customer = customer;
        this.status = "CREATED";
    }

    public Order(Long id, Customer customer, List<OrderLine> lines, BigDecimal total, String status) {
        this.id = id;
        this.customer = customer;
        this.lines.addAll(lines);
        this.total = total;
        this.status = status;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public BigDecimal calculateTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderLine line : lines) {
            sum = sum.add(line.lineTotal());
        }
        return sum;
    }

    public void confirm() {
        this.total = calculateTotal();
        this.status = "CONFIRMED";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
