package com.example.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        order.addLine(new OrderLine(productA, new Quantity(2), Money.of(new BigDecimal("10.00"))));
        order.addLine(new OrderLine(productB, new Quantity(3), Money.of(new BigDecimal("5.00"))));

        Money total = order.calculateTotal();

        assertThat(total).isEqualTo(Money.of(new BigDecimal("35.00")));
    }

    @Test
    void dado_pedido_recien_creado_no_tiene_id_todavia() {
        Order order = new Order(new Customer("Ana", "ana@test.com"));

        assertThat(order.getId()).isEmpty();
    }

    @Test
    void dado_cantidad_cero_o_negativa_no_se_puede_crear_una_linea() {
        assertThatThrownBy(() -> new Quantity(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Quantity(-1)).isInstanceOf(IllegalArgumentException.class);
    }
}
