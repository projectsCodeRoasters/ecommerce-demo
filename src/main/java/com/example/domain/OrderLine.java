package com.example.domain;

import com.example.Product;
import java.math.BigDecimal;

public class OrderLine {

    private Long id;
    private final Product product;
    private final int quantity;
    // precio "congelado" en el momento del pedido — copiado de Product.price
    private final BigDecimal unitPrice;

    public OrderLine(Product product, int quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderLine(Long id, Product product, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
