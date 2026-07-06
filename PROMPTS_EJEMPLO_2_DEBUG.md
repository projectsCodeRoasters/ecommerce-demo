# Ejemplo 2 · Debug: Depurar un bug

## ❌ Prompt pobre

```
"¿Por qué falla este test?"
```

### Resultado
Claude tiene que preguntar qué test, pedir el stacktrace, pedir el código relacionado — tres turnos perdidos antes de empezar a diagnosticar.

**Problema:** Sin información concreta, Claude reproduce tu propio proceso de investigación desde cero.

---

## ✅ Prompt bien construido

```xml
<contexto>Java 21, Spring Boot 3.3</contexto>
<fallo>
  Test: OrderServiceTest.
    dado_stock_insuficiente_lanza_excepcion()
  Esperado: InsufficientStockException
  Obtenido: NullPointerException
  en OrderService.java:42
</fallo>
<ya_intentado>
  Comprobé que el stock del producto
  de test es 0. El repositorio mock
  devuelve Optional.empty() para
  findById.
</ya_intentado>
<tarea>
  Diagnostica la causa raíz.
  Propón el fix mínimo.
</tarea>
```

### Resultado
→ Claude va directo a la causa: falta un `.orElseThrow()`

**Ventaja:** Claude tiene todo (stacktrace, línea, contexto, qué ya probaste) y va directo al diagnóstico.

---

## Clave

**El stacktrace completo + la línea exacta + "qué ya probaste" ahorran turnos de ida y vuelta.**

Lo que debes incluir siempre en un bug report:
1. **Stacktrace o error exacto**
2. **Línea específica donde falla**
3. **Qué ya descartaste** (no hagas que Claude lo vuelva a probar)
4. **Contexto del stack del proyecto**

Sin esto, Claude reproduce tu propio proceso de diagnóstico desde cero. Con esto, va directo a la causa.
