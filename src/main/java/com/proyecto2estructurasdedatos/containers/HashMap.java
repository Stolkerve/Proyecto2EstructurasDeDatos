package com.proyecto2estructurasdedatos.containers;

/**
 * Contenedor de llave valor basado en la estructura std::unordered_map
 * de la libreria estandar de c++. Con insersion y busqueda
 * O(1) lo mas posible
 * @author sebas
 * @param <T> Key type
 * @param <K> Value type
 */
public class HashMap<T, K> {
    private int bucketSize = 50;
    private List<Pair<T, K>>[] buckets;
    private int totalElements = 0;
    private float maxLoadFactor = 1.0f;

    public HashMap() {
        buckets = new List[bucketSize];
    }

    public Pair<T, K> find(T key) {
        for (var i : buckets[hash(key)]) {
            if (i.first.equals(key))
                return i;
        }
        return null;
    }

    // modifica el valor si existe!
    public void insert(T key, K value) {
        var pair = find(key);
        if (pair != null) {
            pair.secound = value;
            return;
        }

        buckets[hash(key)].pushBack(new Pair<>(key, value));
        totalElements++;

        this.rehash();
    }

    private void rehash() {
        if (Float.intBitsToFloat(totalElements) / Float.intBitsToFloat(bucketSize) <= maxLoadFactor)
            return;
        
        bucketSize *= 2;
        List<Pair<T, K>>[] newBuckets = new List[bucketSize];
        for (int i = 0; i < bucketSize / 2; i++) {
            for (var pair: buckets[i]) {
                newBuckets[hash(pair.first)].pushBack(pair);
            }
        }
        buckets = newBuckets;
    }

    private int hash(T key) {
        return key.hashCode() % bucketSize;
    }
}
