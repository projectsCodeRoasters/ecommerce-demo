package com.example.domain;

import com.example.Product;

public class OrderLine {

    private Long id;
    private final Product product;
    private final Quantity quantity;
    // precio "congelado" en el momento del pedido — copiado de Product.price
    private final Money unitPrice;

    public OrderLine(Product product, Quantity quantity, Money unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderLine(Long id, Product product, Quantity quantity, Money unitPrice) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Money lineTotal() {
        return unitPrice.multiply(quantity.value());
    }
}
