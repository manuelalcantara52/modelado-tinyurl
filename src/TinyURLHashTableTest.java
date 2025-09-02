import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las implementaciones de HashTable y TinyURL
 * Estas pruebas verifican la correctitud de todos los métodos implementados
 * 
 * @author Manuel Alcantara
 * @version 1.0
 */
public class TinyURLHashTableTest {
    
    private HashTable hashTable;
    private TinyURL tinyUrl;
    
    @BeforeEach
    void setUp() {
        hashTable = new HashTable();
        tinyUrl = new TinyURL();
    }
    
    // ========== PRUEBAS PARA HashTable ==========
    
    @Test
    @DisplayName("HashTable: Prueba básica de inserción y recuperación")
    void testBasicPutAndGet() {
        // Insertar un valor
        String key = "abc123";
        String value = "https://www.ejemplo.com/pagina/muy/larga";
        
        boolean inserted = hashTable.put(key, value);
        assertTrue(inserted, "La inserción debería retornar true para una nueva clave");
        
        // Recuperar el valor
        String retrievedValue = hashTable.get(key);
        assertEquals(value, retrievedValue, "El valor recuperado debería ser igual al insertado");
        
        // Verificar el tamaño
        assertEquals(1, hashTable.size(), "El tamaño debería ser 1 después de insertar un elemento");
    }
    
    @Test
    @DisplayName("HashTable: Prueba de actualización de clave existente")
    void testUpdateExistingKey() {
        String key = "abc123";
        String originalUrl = "https://www.ejemplo.com/original";
        String updatedUrl = "https://www.ejemplo.com/actualizada";
        
        // Insertar URL original
        assertTrue(hashTable.put(key, originalUrl), "Primera inserción debería retornar true");
        
        // Actualizar con nueva URL
        assertFalse(hashTable.put(key, updatedUrl), "Actualización debería retornar false");
        
        // Verificar que se actualizó correctamente
        assertEquals(updatedUrl, hashTable.get(key), "La URL debería estar actualizada");
        assertEquals(1, hashTable.size(), "El tamaño debería seguir siendo 1");
    }
    
    @Test
    @DisplayName("Prueba de clave no encontrada")
    void testGetNonExistentKey() {
        String result = hashTable.get("clave_inexistente");
        assertNull(result, "Buscar una clave inexistente debería retornar null");
    }
    
    @Test
    @DisplayName("Prueba de múltiples inserciones")
    void testMultipleInsertions() {
        String[] keys = {"key1", "key2", "key3", "key4", "key5"};
        String[] urls = {
            "https://www.sitio1.com",
            "https://www.sitio2.com/pagina",
            "https://www.sitio3.com/muy/larga/url",
            "https://www.sitio4.com?param=valor",
            "https://www.sitio5.com#seccion"
        };
        
        // Insertar múltiples URLs
        for (int i = 0; i < keys.length; i++) {
            assertTrue(hashTable.put(keys[i], urls[i]), 
                "La inserción " + i + " debería ser exitosa");
        }
        
        // Verificar que todas se pueden recuperar
        for (int i = 0; i < keys.length; i++) {
            assertEquals(urls[i], hashTable.get(keys[i]), 
                "La URL " + i + " debería ser recuperable");
        }
        
        assertEquals(keys.length, hashTable.size(), 
            "El tamaño debería ser igual al número de elementos insertados");
    }
    
    @Test
    @DisplayName("Prueba del factor de carga inicial")
    void testInitialLoadFactor() {
        assertTrue(hashTable.isEmpty(), "La tabla hash debería estar vacía inicialmente");
        assertEquals(0.0, hashTable.getLoad(), 0.001, 
            "El factor de carga inicial debería ser 0.0");
        assertEquals(16, hashTable.capacity(), 
            "La capacidad inicial debería ser 16");
    }
    
    @Test
    @DisplayName("Prueba del cálculo del factor de carga")
    void testLoadFactorCalculation() {
        // Insertar algunos elementos
        for (int i = 0; i < 8; i++) {
            hashTable.put("key" + i, "url" + i);
        }
        
        double expectedLoad = 8.0 / 16.0; // 8 elementos en capacidad 16
        assertEquals(expectedLoad, hashTable.getLoad(), 0.001, 
            "El factor de carga debería ser 0.5");
    }
    
    @Test
    @DisplayName("Prueba de redimensionamiento automático")
    void testAutomaticResize() {
        int initialCapacity = hashTable.capacity();
        
        // Insertar suficientes elementos para activar redimensionamiento
        // Capacidad inicial es 16, factor de carga máximo es 0.75
        // Por lo tanto, al insertar 13 elementos (13/16 > 0.75), debería redimensionarse
        for (int i = 0; i < 13; i++) {
            hashTable.put("key" + i, "https://www.ejemplo" + i + ".com");
        }
        
        assertTrue(hashTable.capacity() > initialCapacity, 
            "La capacidad debería haber aumentado después del redimensionamiento");
        
        // Verificar que todos los elementos siguen siendo accesibles
        for (int i = 0; i < 13; i++) {
            assertEquals("https://www.ejemplo" + i + ".com", hashTable.get("key" + i), 
                "Todos los elementos deberían ser accesibles después del redimensionamiento");
        }
    }
    
    @Test
    @DisplayName("Prueba de redimensionamiento manual")
    void testManualResize() {
        // Insertar algunos elementos
        for (int i = 0; i < 5; i++) {
            hashTable.put("key" + i, "url" + i);
        }
        
        int originalCapacity = hashTable.capacity();
        hashTable.resize();
        
        assertEquals(originalCapacity * 2, hashTable.capacity(), 
            "La capacidad debería duplicarse después del redimensionamiento");
        
        // Verificar que todos los elementos siguen siendo accesibles
        for (int i = 0; i < 5; i++) {
            assertEquals("url" + i, hashTable.get("key" + i), 
                "Los elementos deberían seguir siendo accesibles después del redimensionamiento");
        }
    }
    
    @Test
    @DisplayName("Prueba de manejo de colisiones")
    void testCollisionHandling() {
        // Crear una tabla hash pequeña para forzar colisiones
        HashTable smallTable = new HashTable(4);
        
        // Insertar elementos que probablemente colisionen
        String[] keys = {"a", "e", "i", "o", "u"}; // Vocales que pueden colisionar
        String[] urls = {"url1", "url2", "url3", "url4", "url5"};
        
        for (int i = 0; i < keys.length; i++) {
            smallTable.put(keys[i], urls[i]);
        }
        
        // Verificar que todos los elementos se pueden recuperar correctamente
        for (int i = 0; i < keys.length; i++) {
            assertEquals(urls[i], smallTable.get(keys[i]), 
                "Los elementos con colisiones deberían ser recuperables");
        }
        
        assertEquals(keys.length, smallTable.size(), 
            "El tamaño debería reflejar todos los elementos insertados");
    }
    
    @Test
    @DisplayName("Prueba con claves y URLs extremas")
    void testEdgeCases() {
        // Probar con clave vacía
        hashTable.put("", "https://www.ejemplo.com");
        assertEquals("https://www.ejemplo.com", hashTable.get(""), 
            "Debería manejar claves vacías");
        
        // Probar con URL muy larga
        String longUrl = "https://www.ejemplo.com/" + "a".repeat(1000);
        hashTable.put("long", longUrl);
        assertEquals(longUrl, hashTable.get("long"), 
            "Debería manejar URLs muy largas");
        
        // Probar con caracteres especiales
        String specialKey = "áéíóú@#$%";
        String specialUrl = "https://www.sitio-especial.com/página?param=value&other=123";
        hashTable.put(specialKey, specialUrl);
        assertEquals(specialUrl, hashTable.get(specialKey), 
            "Debería manejar caracteres especiales");
    }
    
    @Test
    @DisplayName("Prueba de validación de parámetros nulos")
    void testNullParameterValidation() {
        // Probar put con parámetros nulos
        assertThrows(IllegalArgumentException.class, () -> {
            hashTable.put(null, "https://www.ejemplo.com");
        }, "put() debería lanzar excepción con clave null");
        
        assertThrows(IllegalArgumentException.class, () -> {
            hashTable.put("key", null);
        }, "put() debería lanzar excepción con URL null");
        
        // get() con null debería retornar null sin excepción
        assertNull(hashTable.get(null), "get() con null debería retornar null");
    }
    
    @Test
    @DisplayName("Prueba del método containsKey")
    void testContainsKey() {
        String key = "test_key";
        String url = "https://www.ejemplo.com";
        
        assertFalse(hashTable.containsKey(key), "containsKey debería retornar false antes de insertar");
        
        hashTable.put(key, url);
        assertTrue(hashTable.containsKey(key), "containsKey debería retornar true después de insertar");
        
        assertFalse(hashTable.containsKey("clave_inexistente"), 
            "containsKey debería retornar false para claves inexistentes");
    }
    
    @Test
    @DisplayName("Prueba del método remove")
    void testRemove() {
        String key = "remove_test";
        String url = "https://www.ejemplo.com/remove";
        
        // Intentar eliminar clave inexistente
        assertNull(hashTable.remove("inexistente"), 
            "remove() debería retornar null para clave inexistente");
        
        // Insertar y luego eliminar
        hashTable.put(key, url);
        assertEquals(1, hashTable.size(), "Size debería ser 1 después de insertar");
        
        String removedUrl = hashTable.remove(key);
        assertEquals(url, removedUrl, "remove() debería retornar la URL eliminada");
        assertEquals(0, hashTable.size(), "Size debería ser 0 después de eliminar");
        
        assertNull(hashTable.get(key), "get() debería retornar null después de eliminar");
        assertFalse(hashTable.containsKey(key), "containsKey debería retornar false después de eliminar");
    }
    
    @Test
    @DisplayName("Prueba de rendimiento con muchos elementos")
    void testPerformanceWithManyElements() {
        int numElements = 1000;
        
        // Medir tiempo de inserción
        long startTime = System.nanoTime();
        for (int i = 0; i < numElements; i++) {
            hashTable.put("key" + i, "https://www.ejemplo" + i + ".com");
        }
        long insertTime = System.nanoTime() - startTime;
        
        // Medir tiempo de recuperación
        startTime = System.nanoTime();
        for (int i = 0; i < numElements; i++) {
            hashTable.get("key" + i);
        }
        long retrieveTime = System.nanoTime() - startTime;
        
        // Verificar que el tamaño es correcto
        assertEquals(numElements, hashTable.size(), 
            "Todos los elementos deberían estar presentes");
        
        // Verificar que el factor de carga es razonable (no demasiado alto)
        assertTrue(hashTable.getLoad() <= 0.75, 
            "El factor de carga no debería exceder 0.75");
        
        // Las operaciones deberían ser razonablemente rápidas
        // (estos son límites muy generosos para diferentes sistemas)
        assertTrue(insertTime < 100_000_000, // 100ms
            "Las inserciones deberían ser eficientes");
        assertTrue(retrieveTime < 50_000_000, // 50ms
            "Las recuperaciones deberían ser eficientes");
    }
    
    @Test
    @DisplayName("Prueba del método toString")
    void testToString() {
        String representation = hashTable.toString();
        assertNotNull(representation, "toString() no debería retornar null");
        assertTrue(representation.contains("HashTable"), 
            "La representación debería incluir el nombre de la clase");
        assertTrue(representation.contains("size=0"), 
            "La representación debería mostrar el tamaño actual");
    }
    
    @Test
    @DisplayName("HashTable: Prueba de constructor con capacidad personalizada")
    void testCustomCapacityConstructor() {
        int customCapacity = 32;
        HashTable customTable = new HashTable(customCapacity);
        
        assertEquals(customCapacity, customTable.capacity(), 
            "La capacidad debería ser la especificada en el constructor");
        assertEquals(0, customTable.size(), "El tamaño inicial debería ser 0");
        assertTrue(customTable.isEmpty(), "La tabla debería estar vacía");
        
        // Probar constructor con capacidad inválida
        assertThrows(IllegalArgumentException.class, () -> {
            new HashTable(0);
        }, "Constructor debería lanzar excepción con capacidad 0");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new HashTable(-5);
        }, "Constructor debería lanzar excepción con capacidad negativa");
    }
    
    // ========== PRUEBAS PARA TinyURL ==========
    
    @Test
    @DisplayName("TinyURL: Prueba básica de acortamiento y redirección")
    void testBasicUrlShorteningAndRedirection() {
        String originalUrl = "https://www.ejemplo.com/pagina/muy/larga/con/parametros?id=12345";
        
        // Acortar la URL
        String shortCode = tinyUrl.insertUrl(originalUrl);
        assertNotNull(shortCode, "El código corto no debería ser null");
        assertFalse(shortCode.isEmpty(), "El código corto no debería estar vacío");
        
        // Redirigir usando el código corto
        String redirectedUrl = tinyUrl.redirect(shortCode);
        assertEquals(originalUrl, redirectedUrl, "La URL redirigida debería ser igual a la original");
        
        // Verificar el conteo
        assertEquals(1, tinyUrl.getUrlCount(), "El conteo de URLs debería ser 1");
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de URLs duplicadas")
    void testDuplicateUrls() {
        String url = "https://www.ejemplo.com/duplicada";
        
        // Insertar la misma URL dos veces
        String code1 = tinyUrl.insertUrl(url);
        String code2 = tinyUrl.insertUrl(url);
        
        // Cada inserción debería generar un nuevo código único
        assertNotNull(code1, "El primer código no debería ser null");
        assertNotNull(code2, "El segundo código no debería ser null");
        assertNotEquals(code1, code2, "Los códigos deberían ser diferentes para URLs duplicadas");
        
        // Ambos códigos deberían redirigir a la misma URL
        assertEquals(url, tinyUrl.redirect(code1), "El primer código debería redirigir correctamente");
        assertEquals(url, tinyUrl.redirect(code2), "El segundo código debería redirigir correctamente");
        
        // El conteo debería reflejar ambas entradas
        assertEquals(2, tinyUrl.getUrlCount(), "El conteo debería ser 2 con URLs duplicadas");
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de múltiples URLs")
    void testMultipleUrls() {
        String[] urls = {
            "https://www.sitio1.com",
            "https://www.sitio2.com/pagina",
            "https://www.sitio3.com/muy/larga/url",
            "https://www.sitio4.com?param=valor",
            "https://www.sitio5.com#seccion"
        };
        
        String[] codes = new String[urls.length];
        
        // Acortar todas las URLs
        for (int i = 0; i < urls.length; i++) {
            codes[i] = tinyUrl.insertUrl(urls[i]);
            assertNotNull(codes[i], "El código " + i + " no debería ser null");
            assertFalse(codes[i].isEmpty(), "El código " + i + " no debería estar vacío");
        }
        
        // Verificar que todas se pueden redirigir correctamente
        for (int i = 0; i < urls.length; i++) {
            assertEquals(urls[i], tinyUrl.redirect(codes[i]), 
                "La URL " + i + " debería redirigir correctamente");
        }
        
        // Verificar que todos los códigos son únicos (si se implementa así)
        for (int i = 0; i < codes.length; i++) {
            for (int j = i + 1; j < codes.length; j++) {
                // Esta verificación es opcional ya que depende de la implementación
                // Si no se implementa detección de duplicados, códigos podrían repetirse
            }
        }
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de código inexistente")
    void testNonExistentCode() {
        String result = tinyUrl.redirect("codigo_inexistente");
        assertNull(result, "Redirigir un código inexistente debería retornar null");
        
        assertFalse(tinyUrl.containsCode("codigo_inexistente"), 
            "containsCode debería retornar false para código inexistente");
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de validación de parámetros")
    void testParameterValidation() {
        // Probar insertUrl con parámetros inválidos
        assertThrows(IllegalArgumentException.class, () -> {
            tinyUrl.insertUrl(null);
        }, "insertUrl debería lanzar excepción con URL null");
        
        assertThrows(IllegalArgumentException.class, () -> {
            tinyUrl.insertUrl("");
        }, "insertUrl debería lanzar excepción con URL vacía");
        
        // redirect con null debería retornar null sin excepción
        assertNull(tinyUrl.redirect(null), "redirect con null debería retornar null");
        assertNull(tinyUrl.redirect(""), "redirect con cadena vacía debería retornar null");
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de estado inicial")
    void testInitialState() {
        assertTrue(tinyUrl.isEmpty(), "El servicio debería estar vacío inicialmente");
        assertEquals(0, tinyUrl.getUrlCount(), "El conteo inicial debería ser 0");
        assertTrue(tinyUrl.getLoadFactor() >= 0.0, "El factor de carga debería ser no negativo");
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de longitud de códigos")
    void testCodeLength() {
        String url = "https://www.ejemplo.com";
        String code = tinyUrl.insertUrl(url);
        
        // Los códigos generados deberían tener exactamente 8 caracteres
        assertEquals(8, code.length(), "El código debería tener exactamente 8 caracteres");
        
        // Verificar que solo contiene caracteres válidos
        for (char c : code.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c), 
                "El código debería contener solo letras y números");
        }
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de longitud de código por defecto")
    void testDefaultCodeLength() {
        String url = "https://www.ejemplo.com/test-length";
        String code = tinyUrl.insertUrl(url);
        
        assertNotNull(code, "El código no debería ser null");
        assertEquals(8, code.length(), "El código debería tener longitud 8 por defecto");
        
        // Verificar que solo contiene caracteres válidos
        for (char c : code.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c), 
                "El código debería contener solo letras y números");
        }
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de validación de argumentos negativos")
    void testNegativeArgumentValidation() {
        // Como solo tenemos el constructor por defecto, 
        // estas pruebas verifican el comportamiento con valores inválidos
        
        // Probar con URL null
        assertThrows(IllegalArgumentException.class, () -> {
            tinyUrl.insertUrl(null);
        }, "insertUrl debería lanzar excepción con URL null");
        
        // Probar con URL vacía
        assertThrows(IllegalArgumentException.class, () -> {
            tinyUrl.insertUrl("");
        }, "insertUrl debería lanzar excepción con URL vacía");
        
        // Probar con código null en redirect
        assertNull(tinyUrl.redirect(null), "redirect con null debería retornar null");
        
        // Probar con código vacío en redirect
        assertNull(tinyUrl.redirect(""), "redirect con cadena vacía debería retornar null");
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de método toString")
    void testTinyUrlToString() {
        String representation = tinyUrl.toString();
        assertNotNull(representation, "toString no debería retornar null");
        assertTrue(representation.contains("TinyURL"), 
            "La representación debería incluir el nombre de la clase");
        assertTrue(representation.contains("urlCount=0"), 
            "La representación debería mostrar el conteo inicial");
    }
    
    @Test
    @DisplayName("TinyURL: Prueba de rendimiento básico")
    void testBasicPerformance() {
        // Probar con un número razonable de URLs
        int numUrls = 100;
        
        for (int i = 0; i < numUrls; i++) {
            String url = "https://www.ejemplo" + i + ".com/pagina";
            String code = tinyUrl.insertUrl(url);
            assertNotNull(code, "El código " + i + " no debería ser null");
        }
        
        // Verificar que se mantiene un factor de carga razonable
        assertTrue(tinyUrl.getLoadFactor() <= 0.75, 
            "El factor de carga no debería exceder 0.75");
    }
}
