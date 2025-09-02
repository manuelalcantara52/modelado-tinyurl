/**
 * Implementación de una tabla hash básica
 * Esta clase proporciona una estructura de datos hash genérica para almacenar
 * pares clave-valor usando encadenamiento para el manejo de colisiones
 * 
 * @author [Nombre del Estudiante]
 * @version 1.0
 */
public class HashTable {    
    // Clase interna para representar un par clave-valor
    private static class Entry {        
        public Entry(String key, String value) {
        }
        
        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }
        
    /**
     * Constructor que inicializa la tabla hash con capacidad por defecto
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        // TODO: Implementar el constructor
        // Inicializar la capacidad con DEFAULT_CAPACITY
    }
    
    /**
     * Constructor que permite especificar la capacidad inicial
     * @param initialCapacity Capacidad inicial de la tabla hash
     */
    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        // TODO: Implementar el constructor con capacidad personalizada
    }
    
    /**
     * Función hash que calcula el índice para una clave dada
     * @param key La clave para la cual calcular el hash
     * @return El índice en el array de buckets
     */
    private int hash(String key) {
        // TODO: Implementar la función hash
        // Usar el método hashCode() de String
        return 0;
    }
    
    /**
     * Almacena un valor con su clave correspondiente
     * @param key La clave para el valor
     * @param value El valor a almacenar
     * @return true si la inserción fue exitosa, false si la clave ya existía y se actualizó
     */
    public boolean put(String key, String value) {
        // TODO: Implementar el método put

        return false;
    }
    
    /**
     * Recupera el valor asociado con la clave dada
     * @param key La clave a buscar
     * @return El valor asociado, o null si no se encuentra
     */
    public String get(String key) {
        // TODO: Implementar el método get        
        return null;
    }
    
    /**
     * Calcula y retorna el factor de carga actual de la tabla hash
     * @return El factor de carga (número de elementos / capacidad)
     */
    public double getLoad() {
        // TODO: Implementar el cálculo del factor de carga

        return (double) 0.0;
    }
    
    /**
     * Redimensiona la tabla hash cuando el factor de carga supera el umbral
     * Duplica la capacidad y redistribuye todos los elementos
     */
    @SuppressWarnings("unchecked")
    public void resize() {
        // TODO: Implementar el redimensionamiento
    }
    
    /**
     * Retorna el número actual de elementos en la tabla hash
     * @return El número de elementos almacenados
     */
    public int size() {
        return 0;
    }
    
    /**
     * Retorna la capacidad actual de la tabla hash
     * @return La capacidad actual
     */
    public int capacity() {
        return 0;
    }
    
    /**
     * Verifica si la tabla hash está vacía
     * @return true si no hay elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return false;
    }
    
    /**
     * Verifica si una clave existe en la tabla hash
     * @param key La clave a buscar
     * @return true si la clave existe, false en caso contrario
     */
    public boolean containsKey(String key) {
        return false;
    }
    
    /**
     * Elimina una entrada de la tabla hash
     * @param key La clave de la entrada a eliminar
     * @return El valor que fue eliminado, o null si la clave no existía
     */
    public String remove(String key) {
        // TODO: Implementar el método remove (OPCIONAL)

        return null;
    }
    
    /**
     * Retorna una representación en cadena de la tabla hash para debugging
     * @return String representando el estado actual de la tabla
     */
    @Override
    public String toString() {
        // TODO: Implementar el método toString
        // Incluir información útil como:
        // - Tamaño actual (size)
        // - Capacidad actual (capacity)
        // - Factor de carga actual (loadFactor)
        // - Información sobre buckets no vacíos

        return "HashTable";
    }
}
