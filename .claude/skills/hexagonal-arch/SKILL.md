---
name: hexagonal-arch
description: |
  Refactorizar el módulo de pedidos de este repo a arquitectura
  hexagonal (puertos y adaptadores). Usar cuando se pida "arquitectura
  hexagonal", "puertos y adaptadores", o al separar OrderController /
  OrderService de la persistencia JPA.
---

## Objetivo en este repo

`OrderController` y `OrderService` mezclan HTTP, validación de stock,
cálculo de totales y persistencia JPA en las mismas clases, y devuelven
la entidad `Order` directamente al cliente. Reestructura en tres capas:

```
domain/          → Order, OrderLine, reglas de negocio puras, sin JPA
application/      → casos de uso (puertos de entrada) que orquestan el dominio
infrastructure/   → adaptadores: REST (controller + DTOs), JPA (repos)
```

## Pasos

1. **Puerto de entrada**: define `PlaceOrderUseCase` con un método que
   reciba un comando (no una entidad JPA) y devuelva un identificador o
   resultado, no la entidad completa.
2. **Puerto de salida**: define `OrderRepository` como interfaz en
   `domain`, sin ninguna anotación de Spring/JPA.
3. **Adaptador REST**: `OrderController` solo traduce HTTP ↔ comando /
   respuesta DTO. Cero lógica de negocio, cero acceso a repositorios de
   producto.
4. **Adaptador JPA**: `JpaOrderRepository` implementa el puerto de
   salida y es el único sitio que conoce `@Entity`, `@Repository`, etc.
5. **DTOs de salida**: nunca devolver `Order` (entidad) desde el
   controller — crear un `OrderResponse` explícito.

## Invariante a preservar

El comportamiento HTTP visible (paths, códigos de estado, forma del
JSON de entrada) no debe cambiar salvo que la tarea lo pida. Este es un
refactor de estructura interna, no una reescritura de la API pública.

## Cómo verificar que terminaste

- `OrderController` no importa `OrderRepository` ni `ProductRepository`
  directamente — solo el caso de uso.
- Ninguna clase en `domain/` tiene anotaciones `jakarta.persistence.*`
  ni `org.springframework.*`.
- `mvn compile` pasa sin errores.
