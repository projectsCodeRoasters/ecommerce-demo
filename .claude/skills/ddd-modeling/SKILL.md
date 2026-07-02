---
name: ddd-modeling
description: |
  Modelar el dominio de e-commerce de este repo con DDD táctico:
  entidades, value objects, agregados y lenguaje ubicuo. Usar cuando
  se pida "analiza el dominio", "modela con DDD", "agregado raíz",
  "value object", o al convertir Order/Product/Customer en un modelo
  de dominio explícito en vez de entidades JPA anémicas.
---

## Proceso

1. **Descubre el lenguaje ubicuo primero.** Antes de escribir código,
   pregunta (o infiere del dominio) qué términos usa el negocio:
   ¿"pedido" o "carrito"? ¿"línea de pedido" o "ítem"? Usa esos
   términos exactos en el código — no inventes sinónimos.
2. **Identifica el agregado raíz.** En este repo, `Order` es el
   candidato natural: agrupa `OrderLine`, mantiene el invariante
   "el total siempre es la suma de las líneas" y es el único punto de
   entrada para modificar sus líneas.
3. **Extrae value objects** de los primitivos actuales:
   - `Money` (`BigDecimal` + `Currency`) en vez de `BigDecimal` suelto
     para precios y totales — evita sumar importes en monedas distintas
     por accidente.
   - `OrderId`, `CustomerId`, `ProductId` en vez de `Long` — evita pasar
     un ID de producto donde se espera un ID de pedido.
   - `Quantity` si hay reglas asociadas (no puede ser negativa, etc.).
4. **Protege los invariantes en el constructor / métodos, no con
   setters públicos.** `Order.addLine(...)` debe recalcular el total
   internamente; no debe existir un `setTotal(BigDecimal)` público que
   permita dejarlo inconsistente.
5. **Los value objects son inmutables.** Cualquier operación
   (`Money.add(Money)`) devuelve una instancia nueva, no muta la
   existente.

## Preguntas para descubrir el modelo (hazlas antes de codificar)

- ¿Puede un `Order` existir sin `OrderLine`s, o el pedido nace vacío?
- ¿Qué pasa si se intenta añadir una línea con cantidad 0 o negativa?
- ¿El precio de una línea es el precio actual del producto o el precio
  "congelado" en el momento de añadirla? (Pista: mirar `OrderLine` —
  ya guarda `unitPrice` aparte de `Product.price` por esta razón.)
- ¿`Customer` es una entidad completa aquí, o solo necesitamos su
  identidad (`CustomerId`) desde el agregado `Order`?

## Formato de respuesta

1. Lenguaje ubicuo: breve glosario (término → significado).
2. Lista de entidades/agregados y value objects identificados, con el
   porqué de cada clasificación.
3. Código Java del modelo (agregado raíz + value objects), sin
   anotaciones JPA en el dominio puro — si hace falta persistirlo,
   señalar dónde iría el mapeo (infraestructura), no mezclarlo aquí.

## Restricciones de este repo

- Sin Lombok, sin `null` (usa `Optional` si un valor puede faltar).
- Los value objects se implementan como `record` cuando no necesitan
  lógica de identidad.
