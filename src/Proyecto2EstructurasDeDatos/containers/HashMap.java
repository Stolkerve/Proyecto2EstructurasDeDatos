package Proyecto2EstructurasDeDatos.containers;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Contenedor de llave valor basado en la estructura std::unordered_map
 * de la libreria estandar de c++. Con insersion y busqueda
 * O(1) lo mas posible
 *
 * @param <T> Key type
 * @param <K> Value type
 * @author sebas
 */
public class HashMap<T, K> {
    private List<Pair<T, K>> pairs;
    private int bucketSize = 50;
    private List<Pair<T, K>>[] buckets;
    private int size = 0;
    private float maxLoadFactor = 1.0f;
    private Function<T, Integer> hashFunc;
    private BiFunction<T, T, Boolean> cmpFunc;

    /**
     * Constructor
     */
    public HashMap() {
        pairs = new List<>();
        buckets = new List[bucketSize];
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new List<>();

        hashFunc = (v) -> {
            return v.hashCode();
        };
        cmpFunc = (v1, v2) -> {
            return v1.equals(v2);
        };
    }

    /**
     * @param hashFunc funcion hash personalizada
     * @param cmpFunc funcion de comparacion personalizada
     */
    public HashMap(Function<T, Integer> hashFunc, BiFunction<T, T, Boolean> cmpFunc) {
        pairs = new List<>();
        buckets = new List[bucketSize];
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new List<>();

        this.hashFunc = hashFunc;
        this.cmpFunc = cmpFunc;
    }

    /**
     * @param key LLave
     * @return Retorna el valor relacionado a la llave. Null si no existe
     */
    public Pair<T, K> find(T key) {
        return buckets[hash(key)].forEach(i -> {
            if (cmpFunc.apply(i.first, key))
                return i;
            return null;
        });
    }

    /**
     * Inserta un nuevo valor. Modifica el valor si existe!
     *
     * @param key   Llave
     * @param value Valor
     */
    public void insert(T key, K value) {
        var pair = find(key);
        if (pair != null) {
            pair.secound = value;
            return;
        }

        var newPair = new Pair<>(key, value);
        pairs.pushBack(newPair);
        buckets[hash(key)].pushBack(newPair);
        size++;

        this.rehash();
    }

    /**
     * @return Tamano del HashMap
     */
    public int size() {
        return size;
    }

    /**
     * @return Iterador de HashMap
     */
    public Pair<T, K> forEach(BiFunction<Pair<T, K>, Integer, Pair<T, K>> callback) {
        int i = 0;
        Node<Pair<T, K>> current = pairs.begin;
        while (current != null) {
            var data = current.val;
            current = current.child;
            var ret = callback.apply(data, i);
            if (ret != null) return ret;
            i++;
        }
        return null;
    }

    /**
     * Aumentar el tamano del hashmap
     */
    private void rehash() {
        if (Float.intBitsToFloat(size) / Float.intBitsToFloat(bucketSize) <= maxLoadFactor)
            return;

        bucketSize *= 2;
        List<Pair<T, K>>[] newBuckets = new List[bucketSize];
        for (int i = 0; i < newBuckets.length; i++)
            newBuckets[i] = new List<>();
        for (int i = 0; i < bucketSize / 2; i++) {
            buckets[i].forEach(pair -> {
                newBuckets[hash(pair.first)].pushBack(pair);
                return null;
            });
        }
        buckets = newBuckets;
    }

    /**
     * @param key llave del valor
     * @return index en el bucket
     */
    private int hash(T key) {
        return (hashFunc.apply(key) & 0x7FFFFFFF) % bucketSize;
    }
}