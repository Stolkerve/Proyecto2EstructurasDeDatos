package com.proyecto2estructurasdedatos.containers;

import java.util.Iterator;

/**
 * Contenedor de un elementos enlazados
 * 
 * @author sebas
 */
public class List<T> implements Iterable<T> {
    private int size;
    Node<T> begin;
    Node<T> end;

    public List() {
        this.size = 0;
        this.begin = null;
        this.end = null;
    }

    /**
     * @return Iterador de HashMap
     */
    public Iterator<T> iterator() {
        return new ListIterator<T>(this);
    }

    public int size() {
        return this.size;
    }

    /**
     * @param v Elemento nuevo en el final del vector
     */
    public void pushBack(T v) {
        this.size++;

        if (init(v))
            return;

        this.end.child = new Node<T>(v, null);
        this.end = this.end.child;
    }

    /**
     * @param v Elementos nuevos en el final del vector
     */
    public void pushBack(T[] v) {
        for (T t : v) {
            pushBack(t);
        }
    }

    private boolean init(T v) {
        if (this.begin == null) {
            this.begin = new Node<T>(v, this.end);
            this.end = this.begin;
            return true;
        }
        return false;
    }
}

/**
 * Implementacion de iterador para la clase List
 * @param <T> type
 */
class ListIterator<T> implements Iterator<T> {
    Node<T> current;

    ListIterator(List<T> list) {
        current = list.begin;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        T data = current.val;
        this.current = this.current.child;
        return data;
    }
}
