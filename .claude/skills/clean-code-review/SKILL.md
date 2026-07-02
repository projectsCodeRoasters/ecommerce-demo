---
name: clean-code-review
description: |
  Revisar y refactorizar código Java de este repo aplicando Clean Code.
  Usar cuando se pida "revisar", "code review", "¿está bien este
  código?" o al refactorizar clases con nombres poco claros o métodos
  largos (p. ej. ProductService).
---

## Proceso de revisión

1. Lee el fichero completo antes de comentar nada.
2. Detecta, en este orden de prioridad:
   - Nombres sin semántica (`d`, `f`, `chk`, `l`, `x`) → renombrar a su
     intención real.
   - Métodos que hacen más de una cosa → extraer método por
     responsabilidad.
   - `return null` en vez de `Optional<T>`.
   - Comentarios que explican el QUÉ (`// Calcula el precio con
     descuento`) en vez de un WHY no obvio → si el nombre ya lo dice,
     eliminar el comentario.
   - Anidamiento profundo de `if/else` → invertir condiciones o
     extraer métodos con nombres que expliquen cada rama.
3. Refactoriza manteniendo el comportamiento exacto. Si no hay tests
   que lo garanticen, escribe primero un test de caracterización antes
   de tocar el código (no refactorices a ciegas).

## Formato de respuesta

- Lista de issues encontrados, con severidad (alta/media/baja) y la
  línea o método afectado.
- Código refactorizado completo del fichero.
- Si se pidió "solo código", omite la lista de issues y entrega
  únicamente el fichero refactorizado.

## Referencia rápida de nombres en este repo

| Antes | Significado real | Después |
|---|---|---|
| `d(l, x)` | aplica descuento según tipo de cliente | `applyDiscount(products, customerType)` |
| `f(n)` | busca producto por nombre | `findByName(name)` |
| `chk(id, q)` | comprueba stock suficiente | `hasEnoughStock(productId, quantity)` |
