---
name: tdd-junit
description: |
  Guía TDD con JUnit 5 y AssertJ para este repo. Usar cuando se pidan
  tests primero, ciclo red-green-refactor, o "aplica TDD" sobre
  cualquier clase del proyecto (p.ej. OrderService.calculateTotal()).
---

## Flujo

1. **RED** — Escribe el test antes que la implementación. Ejecuta
   `mvn test` y confirma que falla por el motivo esperado (método no
   existe / comportamiento incorrecto), no por un error de compilación
   accidental.
2. **GREEN** — Implementa el mínimo código necesario para que el test
   pase. No añadas nada que el test no exija.
3. **REFACTOR** — Con el test en verde, limpia el código (nombres,
   duplicación, estructura) sin cambiar el comportamiento. Vuelve a
   ejecutar los tests tras cada cambio.

## Convenciones de este repo

- Tests en español, nombres con el patrón `dado_cuando_entonces`.
- AssertJ para las aserciones (`assertThat(...)`), no `assertEquals`.
- Un `@Test` prueba un único comportamiento.
- Sin mocks para lógica de dominio pura (p. ej. `calculateTotal`) —
  solo mockear puertos externos (repositorios, servicios).

## Ejemplo de arranque

```java
@Test
void dado_pedido_con_dos_lineas_calcula_total_correcto() {
    Order order = new Order(new Customer("Ana", "ana@test.com"));
    order.addLine(new OrderLine(productA, 2, new BigDecimal("10.00")));
    order.addLine(new OrderLine(productB, 3, new BigDecimal("5.00")));

    BigDecimal total = orderService.calculateTotal(order);

    assertThat(total).isEqualByComparingTo("35.00");
}
```

## Al terminar

Confirma que `mvn test` pasa en verde antes de dar la tarea por
completada. No dejes tests en rojo ni comentados.
