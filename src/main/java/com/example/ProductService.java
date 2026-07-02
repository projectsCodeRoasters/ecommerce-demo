package com.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final BigDecimal VIP_HIGH_VALUE_THRESHOLD = BigDecimal.valueOf(100);
    private static final BigDecimal VIP_HIGH_VALUE_DISCOUNT = BigDecimal.valueOf(0.7);
    private static final BigDecimal VIP_STANDARD_DISCOUNT = BigDecimal.valueOf(0.8);
    private static final int VIP_HIGH_STOCK_THRESHOLD = 50;
    private static final BigDecimal VIP_HIGH_STOCK_EXTRA_DISCOUNT = BigDecimal.valueOf(0.95);

    private static final BigDecimal REGULAR_DISCOUNT = BigDecimal.valueOf(0.9);
    private static final int REGULAR_LOW_STOCK_THRESHOLD = 5;
    private static final BigDecimal REGULAR_LOW_STOCK_SURCHARGE = BigDecimal.valueOf(1.1);

    private static final BigDecimal CLEARANCE_DISCOUNT = BigDecimal.valueOf(0.5);

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> applyDiscount(List<Product> products, int customerType) {
        if (products == null) {
            return Optional.empty();
        }
        Product lastUpdated = null;
        for (Product product : products) {
            if (product == null) {
                continue;
            }
            product.setPrice(discountedPriceFor(product, customerType));
            productRepository.save(product);
            lastUpdated = product;
        }
        return Optional.ofNullable(lastUpdated);
    }

    private BigDecimal discountedPriceFor(Product product, int customerType) {
        return switch (customerType) {
            case 1 -> vipPrice(product);
            case 2 -> regularPrice(product);
            case 3 -> product.getPrice().multiply(CLEARANCE_DISCOUNT);
            default -> product.getPrice();
        };
    }

    private BigDecimal vipPrice(Product product) {
        BigDecimal price = product.getPrice();
        price = isHighValue(price)
                ? price.multiply(VIP_HIGH_VALUE_DISCOUNT)
                : price.multiply(VIP_STANDARD_DISCOUNT);
        if (product.getStock() > VIP_HIGH_STOCK_THRESHOLD) {
            price = price.multiply(VIP_HIGH_STOCK_EXTRA_DISCOUNT);
        }
        return price;
    }

    private BigDecimal regularPrice(Product product) {
        BigDecimal price = product.getPrice().multiply(REGULAR_DISCOUNT);
        if (product.getStock() < REGULAR_LOW_STOCK_THRESHOLD) {
            price = price.multiply(REGULAR_LOW_STOCK_SURCHARGE);
        }
        return price;
    }

    private boolean isHighValue(BigDecimal price) {
        return price.compareTo(VIP_HIGH_VALUE_THRESHOLD) > 0;
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findAll().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public boolean hasEnoughStock(Long productId, int quantity) {
        return productRepository.findById(productId)
                .map(product -> product.getStock() >= quantity)
                .orElse(false);
    }
}
