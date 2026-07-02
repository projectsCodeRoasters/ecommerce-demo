package com.example.infrastructure.rest.dto;

import com.example.Product;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price, int stock) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }
}
