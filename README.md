# Evaluación: Implementación de Servicio TinyURL usando Tabla Hash

**Modelado y Programación**

## Descripción del Problema

En esta evaluación, implementarás un servicio básico similar a TinyURL que permite acortar URLs largas y convertirlas en versiones más cortas y manejables. El objetivo principal es crear una tabla hash eficiente que pueda almacenar y recuperar las URLs de manera rápida, junto con una clase TinyURL que utilice esta tabla hash para proporcionar la funcionalidad de acortamiento de URLs.

### ¿Qué es TinyURL?

TinyURL es un servicio web que toma URLs largas y las convierte en versiones más cortas. Por ejemplo:

- URL original: `https://www.ejemplo.com/pagina/muy/larga/con/muchos/parametros?id=12345`
- URL acortada: `http://tiny.ly/abc123`

### Funcionamiento del Sistema

El sistema debe funcionar de la siguiente manera:

1. **Acortar URL**: Cuando un usuario proporciona una URL larga, el sistema genera una clave única (código corto) y almacena la relación en la tabla hash.

2. **Expandir URL**: Cuando un usuario accede a la URL corta, el sistema busca la URL original en la tabla hash y redirige al usuario.

3. **Gestión de capacidad**: La tabla hash debe poder redimensionarse automáticamente cuando el factor de carga sea demasiado alto para mantener un rendimiento óptimo.

## Requisitos de Implementación

Debes implementar dos clases que trabajen en conjunto:

### Clase: HashTable

Una tabla hash genérica con los siguientes métodos:

1. `put(String key, String value)`: Almacena un valor con su clave correspondiente.

2. `get(String key)`: Recupera el valor asociado con la clave dada.

3. `getLoad()`: Calcula y retorna el factor de carga actual de la tabla hash.

4. `resize()`: Redimensiona la tabla hash cuando el factor de carga supera un umbral definido.

5. `toString()`: Retorna una representación en cadena del estado actual de la tabla hash.

### Clase: TinyURL

Un servicio que utiliza la tabla hash para acortar URLs:

1. `insertUrl(String url)`: Recibe una URL larga y retorna un código corto único de 8 caracteres.

2. `redirect(String code)`: Recibe un código corto y retorna la URL original correspondiente.

3. `generateRandomCode()`: Método privado que genera códigos aleatorios de 8 caracteres.

4. `generateUniqueCode()`: Método privado que garantiza códigos únicos en el sistema.

### Detalles de Implementación para TinyURL

#### Variable CHARACTERS

La clase TinyURL debe incluir una constante que defina los caracteres permitidos:

```java
private static final String CHARACTERS = 
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
```

Esta cadena contiene:
- 26 letras minúsculas (a-z)
- 26 letras mayúsculas (A-Z) 
- 10 dígitos (0-9)
- **Total: 62 caracteres diferentes**

#### Implementación de generateRandomCode()

Para implementar este método:

1. Crear un `StringBuilder` para construir el código
2. Usar un bucle for de 8 iteraciones (DEFAULT_CODE_LENGTH = 8)
3. En cada iteración:
   - Generar un índice aleatorio: `random.nextInt(CHARACTERS.length())`
   - Obtener el carácter: `CHARACTERS.charAt(randomIndex)`
   - Agregarlo al StringBuilder
4. Retornar `code.toString()`

### Consideraciones Técnicas

- **Función Hash**: Debes implementar una función hash que distribuya las claves de manera uniforme.

- **Manejo de Colisiones**: Utiliza encadenamiento (chaining) con listas enlazadas para manejar colisiones.

- **Factor de Carga**: Mantén un factor de carga máximo de 0.75 para garantizar un rendimiento óptimo.

- **Redimensionamiento**: Cuando el factor de carga supere 0.75, duplica el tamaño de la tabla y rehash todos los elementos.

- **Clase Entry**: Dentro de HashTable, utiliza una clase interna `Entry` para representar pares clave-valor. Esta clase debe contener los campos `key` y `value` de tipo String, junto con un constructor y el método `equals()` para comparar entradas por clave.

## Especificaciones de Rendimiento

### HashTable
- **Complejidad esperada para put()**: O(1) promedio
- **Complejidad esperada para get()**: O(1) promedio
- **Complejidad esperada para getLoad()**: O(1)
- **Complejidad para resize()**: O(n) donde n es el número de elementos

### TinyURL
- **Complejidad esperada para insertUrl()**: O(1) promedio
- **Complejidad esperada para redirect()**: O(1) promedio

## Entregables

1. Implementación completa de la clase `HashTable`
2. Implementación completa de la clase `TinyURL`
3. Documentación de código con comentarios explicativos
4. Tu implementación debe pasar todas las pruebas unitarias proporcionadas

## Criterios de Evaluación

- **Correctitud (40%)**: La implementación debe funcionar correctamente y pasar todas las pruebas.

- **Eficiencia (30%)**: El código debe cumplir con las complejidades temporales esperadas.

- **Calidad del código (20%)**: Código limpio, bien documentado y siguiendo buenas prácticas.

- **Manejo de casos edge (10%)**: Manejo adecuado de casos especiales y errores.

## Preguntas de Análisis

Responde las siguientes preguntas sobre el servicio TinyURL implementado:

1. **Capacidad del sistema**: Con códigos de 8 caracteres y 62 caracteres diferentes disponibles, ¿cuántas URLs únicas puede almacenar teóricamente el sistema? Justifica tu cálculo.

2. **Probabilidad de colisiones**: Si tenemos 1 millón de URLs almacenadas, ¿cuál es la probabilidad aproximada de que se genere un código duplicado al insertar una nueva URL? ¿Cómo afecta esto al rendimiento?

3. **Análisis de rendimiento**: ¿Cuál es la complejidad temporal del método `generateUniqueCode()` en el peor caso? ¿Qué factores afectan esta complejidad y cómo podrías optimizarla?

4. **Escalabilidad**: Si el sistema necesita manejar 100 millones de URLs, ¿qué modificaciones harías al diseño actual? Considera tanto la longitud de los códigos como la estructura de datos subyacente.

5. **Comparación de enfoques**: ¿Cuáles son las ventajas y desventajas de permitir URLs duplicadas (generar códigos diferentes) versus detectar y reutilizar códigos existentes? Analiza el impacto en memoria, rendimiento y experiencia de usuario.

## Instrucciones de Compilación y Ejecución

Para compilar y ejecutar las pruebas unitarias, sigue estos pasos:

### Paso 1: Compilar las Clases

```bash
javac HashTable.java TinyURL.java
```

### Paso 2: Descargar JUnit 5

```bash
curl -L -o junit-platform-console-standalone-1.8.0.jar \
https://repo1.maven.org/maven2/org/junit/platform/\
junit-platform-console-standalone/1.8.0/\
junit-platform-console-standalone-1.8.0.jar
```

### Paso 3: Compilar las Pruebas

```bash
javac -cp ".:junit-platform-console-standalone-1.8.0.jar" \
TinyURLHashTableTest.java
```

### Paso 4: Ejecutar las Pruebas

```bash
java -cp ".:junit-platform-console-standalone-1.8.0.jar" \
org.junit.platform.console.ConsoleLauncher \
--classpath . --select-class TinyURLHashTableTest
```

### Resultado Esperado

Al ejecutar las pruebas correctamente, deberías ver un resultado similar a:

```
Test run finished after 57 ms
[        27 tests found           ]
[         0 tests skipped         ]
[        27 tests started         ]
[         0 tests aborted         ]
[        27 tests successful      ]
[         0 tests failed          ]
```

> **Nota**: En sistemas Windows, reemplaza los dos puntos `:` en el classpath por punto y coma `;`.

---

## Estructura de Archivos

```
src/
├── HashTable.java              # Implementación de tabla hash (TODOs pendientes)
├── TinyURL.java                 # Servicio TinyURL (TODOs pendientes)
├── TinyURLHashTableTest.java    # Pruebas unitarias completas
```

## Estado Inicial

El código proporcionado incluye:
- ✅ Estructura completa de ambas clases
- ✅ Esqueletos de métodos con comentarios TODO
- ✅ Suite completa de 27 pruebas unitarias
- ❌ Implementación de métodos (tu tarea)

Al ejecutar las pruebas inicialmente, verás que aproximadamente 24 pruebas fallan - esto es esperado y normal. Tu objetivo es implementar los métodos para que todas las pruebas pasen.

¡Buena suerte con la implementación! 🚀
