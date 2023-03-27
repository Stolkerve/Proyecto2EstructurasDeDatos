package com.proyecto2estructurasdedatos.containers;

/**
 * @author sebas
 * @param <T> First type
 * @param <U> Second type
 */
public class Pair<T, K> {
    public T first;
    public K secound;

    public Pair(T first, K secound) {
        this.first = first;
        this.secound = secound;
    }
}
