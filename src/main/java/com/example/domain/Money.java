package com.example.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

// Value object: un importe siempre va acompañado de su moneda. Inmutable —
// toda operación devuelve una instancia nueva.
public record Money(BigDecimal amount, Currency currency) {

    private static final Currency EUR = Currency.getInstance("EUR");

    public Money {
        Objects.requireNonNull(amount, "amount es obligatorio");
        Objects.requireNonNull(currency, "currency es obligatoria");
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("El importe no puede ser negativo: " + amount);
        }
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount, EUR);
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO, EUR);
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(this.amount.add(other.amount), currency);
    }

    public Money multiply(int factor) {
        return new Money(amount.multiply(BigDecimal.valueOf(factor)), currency);
    }

    private void requireSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "No se pueden operar importes en monedas distintas: " + this.currency + " vs " + other.currency);
        }
    }
}
