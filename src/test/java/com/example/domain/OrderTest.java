package com.example.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.Customer;
import com.example.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void dado_pedido_con_dos_lineas_calcula_total_correcto() {
        Product productA = new Product("Camiseta", new BigDecimal("10.00"), 5);
        Product productB = new Product("Pantalón", new BigDecimal("5.00"), 5);
        Order order = new Order(new Customer("Ana", "ana@test.com"));
        order.addLine(new OrderLine(productA, 2, new BigDecimal("10.00")));
        order.addLine(new OrderLine(productB, 3, new BigDecimal("5.00")));

        BigDecimal total = order.calculateTotal();

        assertThat(total).isEqualByComparingTo("35.00");
    }
}
