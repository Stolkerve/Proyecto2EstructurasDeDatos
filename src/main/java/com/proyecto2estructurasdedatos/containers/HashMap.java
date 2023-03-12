package com.proyecto2estructurasdedatos.containers;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Contenedor de llave valor basado en la estructura std::unordered_map
 * de la libreria estandar de c++. Con insersion y busqueda
 * O(1) lo mas posible
 * 
 * @author sebas
 * @param <T> Key type
 * @param <K> Value type
 */
public class HashMap<T, K> implements Iterable<Pair<T, K>> {
    private List<Pair<T, K>> pairs;
    private int bucketSize = 50;
    private List<Pair<T, K>>[] buckets;
    private int size = 0;
    private float maxLoadFactor = 1.0f;

    public HashMap() {
        pairs = new List<>();
        buckets = new List[bucketSize];
        // Arrays.fill(buckets, new List<>());
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new List<>();
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

        var newPair = new Pair<>(key, value);
        pairs.pushBack(newPair);
        buckets[hash(key)].pushBack(newPair);
        size++;

        this.rehash();
    }

    public int size() {
        return size;
    }

    public Iterator<Pair<T, K>> iterator() {
        return new HashMapIterator<T, K>(pairs);
    }

    private void rehash() {
        if (Float.intBitsToFloat(size) / Float.intBitsToFloat(bucketSize) <= maxLoadFactor)
            return;

        bucketSize *= 2;
        List<Pair<T, K>>[] newBuckets = new List[bucketSize];
        for (int i = 0; i < newBuckets.length; i++)
            newBuckets[i] = new List<>();
        for (int i = 0; i < bucketSize / 2; i++) {
            for (var pair : buckets[i]) {
                newBuckets[hash(pair.first)].pushBack(pair);
            }
        }
        buckets = newBuckets;
    }

    private int hash(T key) {
        int hash = key.hashCode() & 0x7FFFFFFF;
        return hash % bucketSize;
    }
}

class HashMapIterator<T, K> implements Iterator<Pair<T, K>> {
    Node<Pair<T, K>> current;

    HashMapIterator(List<Pair<T, K>> list) {
        current = list.begin;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Pair<T, K> next() {
        var data = current.val;
        this.current = this.current.child;
        return data;
    }
}