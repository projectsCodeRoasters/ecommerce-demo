package com.example;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Entidad JPA expuesta directamente (sin DTO, sin puerto) — a propósito,
// es lo que el ejercicio de arquitectura hexagonal debe corregir.
//
// @Table(name = "orders") es obligatorio: "order" es palabra reservada en
// SQL (ORDER BY) y rompe las foreign keys generadas por Hibernate tanto en
// H2 como en Postgres si se deja el nombre de tabla por defecto.
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> lines = new ArrayList<>();

    private BigDecimal total;

    private String status;

    protected Order() {
        // requerido por JPA
    }

    public Order(Customer customer) {
        this.customer = customer;
        this.status = "CREATED";
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public Long getId() {
        return id;
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

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
