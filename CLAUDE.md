# Proyecto: E-commerce (repo de práctica · Formación Claude Code)

## Stack
- Java 21, Spring Boot 3.3, Maven
- PostgreSQL 16, Spring Data JPA
- JUnit 5 + AssertJ

## Cómo arrancar
- `mvn spring-boot:run` — usa H2 en memoria por defecto, sin dependencias externas.
- `mvn spring-boot:run -Dspring-boot.run.profiles=postgres` — usa Postgres real en `localhost:5432` (requiere tenerlo levantado). Ver perfiles en `application.yml`.

## Estado actual del repo (intencionado)
Este repo empieza **a propósito** en mal estado para la práctica:
- Arquitectura monolítica: lógica de negocio mezclada con JPA y con el controller
- Sin tests (`src/test/java` está vacío)
- Code smells deliberados en `ProductService` (nombres sin semántica, métodos largos)
- `OrderService.calculateTotal()` no está implementado — es el punto de partida del ejercicio de TDD

No "arregles" estos problemas salvo que la tarea lo pida explícitamente — son el objetivo de cada ejercicio (TDD, Clean Code, Hexagonal, DDD).

## Arquitectura objetivo (al refactorizar)
- Hexagonal: `domain` / `application` / `infrastructure`
- NO exponer entidades JPA en controllers — usar DTOs en los adaptadores de entrada
- Puertos de entrada como casos de uso explícitos (`PlaceOrderUseCase`), puertos de salida como interfaces (`OrderRepository`)

## Convenciones
- Código en inglés, tests en español
- Sin Lombok
- Sin `null` — usar `Optional`
- Nombres de tests: `dado_cuando_entonces`

## Restricciones
- Sin dependencias externas sin aprobar
- Los tests deben pasar antes de cualquier commit
