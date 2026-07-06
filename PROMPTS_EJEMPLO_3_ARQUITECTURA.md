# Ejemplo 3 · Decisión de arquitectura: Contexto vs. genéricos

## ❌ Prompt pobre

```
"¿Uso eventos o llamadas síncronas entre servicios?"
```

### Resultado
Respuesta genérica de manual, sin conectar con tu contexto real (volumen, SLA, equipo, infraestructura ya existente).

**Problema:** La pregunta es válida pero sin restricciones concretas, Claude no puede tomar una decisión — solo puede listar trade-offs abstractos.

---

## ✅ Prompt bien construido

```xml
<contexto>
  OrderService necesita notificar a
  InventoryService cuando se confirma
  un pedido. Ya usamos Kafka para
  otros flujos. SLA de confirmación:
  < 200ms percibido por el usuario.
  Volumen: ~50 pedidos/seg en pico.
</contexto>
<tarea>
  Recomienda evento asíncrono vs.
  llamada síncrona. Justifica con
  los trade-offs de ESTE caso
  concreto, no en general.
</tarea>
<output>Tabla de trade-offs +
recomendación final en una frase.</output>
```

### Resultado
→ Recomendación anclada en tu SLA, tu volumen y tu infraestructura ya existente

**Ventaja:** Claude razona sobre *tu* realidad concreta, no sobre abstracciones. La recomendación es accionable.

---

## Clave

**Para decisiones de diseño: dar las restricciones reales importa más que la pregunta en sí.**

Siempre incluye:
1. **Contexto específico:** Qué ya tienes (Kafka, estructura del equipo, etc.)
2. **Restricciones:** SLA, volumen, límites de infraestructura
3. **Por qué importa:** El volumen de 50 req/seg hace que la latencia sea crítica
4. **Output esperado:** Tabla de trade-offs + recomendación en una frase

Sin esto: genérico. Con esto: decisión fundamentada en *tu* realidad.
