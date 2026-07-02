package com.example.infrastructure.persistence;

import com.example.Customer;
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

// @Table(name = "orders") es obligatorio: "order" es palabra reservada en
// SQL (ORDER BY) y rompe las foreign keys generadas por Hibernate tanto en
// H2 como en Postgres si se deja el nombre de tabla por defecto.
@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineJpaEntity> lines = new ArrayList<>();

    private BigDecimal total;

    private String status;

    protected OrderJpaEntity() {
        // requerido por JPA
    }

    public OrderJpaEntity(Long id, Customer customer, List<OrderLineJpaEntity> lines, BigDecimal total, String status) {
        this.id = id;
        this.customer = customer;
        this.lines = lines;
        this.total = total;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderLineJpaEntity> getLines() {
        return lines;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
