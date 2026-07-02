package com.example;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Code smells deliberados para el ejercicio de Clean Code Review:
// nombres sin semántica, método largo, null en vez de Optional,
// comentarios que explican el WHAT en lugar del WHY.
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Calcula el precio con descuento
    public Object d(List<Product> l, int x) {
        if (l == null) {
            return null;
        }
        Object result = null;
        for (int i = 0; i < l.size(); i++) {
            Product p = l.get(i);
            if (p == null) {
                continue;
            }
            BigDecimal pr = p.getPrice();
            if (x == 1) {
                // Cliente VIP: 20% de descuento
                if (pr.compareTo(BigDecimal.valueOf(100)) > 0) {
                    pr = pr.multiply(BigDecimal.valueOf(0.7));
                } else {
                    pr = pr.multiply(BigDecimal.valueOf(0.8));
                }
                if (p.getStock() > 50) {
                    pr = pr.multiply(BigDecimal.valueOf(0.95));
                }
            } else if (x == 2) {
                // Cliente regular: 10% de descuento
                pr = pr.multiply(BigDecimal.valueOf(0.9));
                if (p.getStock() < 5) {
                    pr = pr.multiply(BigDecimal.valueOf(1.1));
                }
            } else if (x == 3) {
                // Liquidación: 50% de descuento
                pr = pr.multiply(BigDecimal.valueOf(0.5));
            } else {
                pr = p.getPrice();
            }
            p.setPrice(pr);
            productRepository.save(p);
            result = p;
        }
        return result;
    }

    // Busca por nombre
    public Product f(String n) {
        List<Product> all = productRepository.findAll();
        for (Product p : all) {
            if (p.getName().equalsIgnoreCase(n)) {
                return p;
            }
        }
        return null;
    }

    // Comprueba si hay stock suficiente
    public boolean chk(Long id, int q) {
        Product p = productRepository.findById(id).orElse(null);
        if (p == null) {
            return false;
        }
        if (p.getStock() >= q) {
            return true;
        }
        return false;
    }
}
