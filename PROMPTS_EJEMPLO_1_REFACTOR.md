# Ejemplo 1 · Refactor: Mismo problema, dos prompts

## ❌ Prompt pobre

```
"Mejora este servicio Java"
```

### Resultado
Claude pregunta qué tipo de mejora, añade Lombok, introduce null checks innecesarios, genera 3 variantes con explicaciones extensas...

**Problema:** Sin contexto, Claude genera respuestas genéricas e inseguras. No sabe qué "mejorar" significa en tu proyecto.

---

## ✅ Prompt bien construido

```xml
<contexto>Java 21, Spring Boot 3.3,
sin Lombok, Optional obligatorio</contexto>
<tarea>
  Extrae la lógica de negocio de
  OrderController a OrderService.
  Principio: un método = una razón
  para cambiar.
</tarea>
<output>Solo los dos ficheros Java,
sin comentarios ni explicaciones.</output>
```

### Resultado
→ Resultado directo, limpio, sin preguntas adicionales

**Ventaja:** Claude entiende las restricciones, el objetivo concreto y cómo devolver el resultado. Genera código que respeta el stack del proyecto.

---

## Clave

**Da contexto antes de pedir.** El contexto es más valioso que la pregunta misma.

- **Contexto:** Stack, convenciones, restricciones
- **Tarea:** Qué exactamente necesitas (no "mejora", sino "extrae OrderService")
- **Output:** Formato esperado (solo código, sin explicaciones)
