package com.example.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void dado_dos_importes_en_la_misma_moneda_los_suma() {
        Money total = Money.of(new BigDecimal("10.00")).add(Money.of(new BigDecimal("5.50")));

        assertThat(total).isEqualTo(Money.of(new BigDecimal("15.50")));
    }

    @Test
    void dado_un_importe_negativo_no_se_puede_crear() {
        assertThatThrownBy(() -> Money.of(new BigDecimal("-1.00")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void dado_importes_en_monedas_distintas_no_se_pueden_sumar() {
        Money euros = new Money(new BigDecimal("10.00"), Currency.getInstance("EUR"));
        Money dolares = new Money(new BigDecimal("10.00"), Currency.getInstance("USD"));

        assertThatThrownBy(() -> euros.add(dolares)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void dado_un_importe_lo_multiplica_por_una_cantidad_de_unidades() {
        Money unitPrice = Money.of(new BigDecimal("10.00"));

        Money lineTotal = unitPrice.multiply(3);

        assertThat(lineTotal).isEqualTo(Money.of(new BigDecimal("30.00")));
    }
}
