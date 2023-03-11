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

    List() {
        this.size = 0;
        this.begin = null;
        this.end = null;
    }

    public Iterator<T> iterator() {
        return new ListIterator<T>(this);
    }

    public int size() {
        return this.size;
    }

    public boolean empty() {
        return this.size == 0;
    }

    public boolean remove(int pos) {
        if (pos > (this.size - 1) || pos < 0)
            return false;
        size--;

        Node<T> prevNode = null;
        Node<T> targetNode = this.begin;

        if (pos == 0) {
            this.begin = this.begin.child;

            if (this.begin == null) {
                this.clear();
            }

            return true;
        }

        for (int i = 0; i < pos; i++) {
            prevNode = targetNode;
            targetNode = targetNode.child;
        }

        prevNode.child = targetNode.child;

        return true;
    }

    public void clear() {
        this.begin = null;
        this.end = null;
        this.size = 0;
    }

    public void pushBack(T v) {
        this.size++;

        if (init(v))
            return;

        this.end.child = new Node<T>(v, null);
        this.end = this.end.child;
    }

    public void pushFront(T v) {
        this.size++;

        if (init(v))
            return;

        Node<T> newNode = new Node<T>(v, this.begin);
        this.begin = newNode;
        this.begin.child = newNode;
    }

    public boolean insert(T v, int pos) {
        if (pos > (this.size - 1) || pos < 0)
            return false;
        this.size++;

        if (init(v))
            return true;

        Node<T> prevNode = this.begin;
        for (int i = 0; i < pos; i++) {
            prevNode = prevNode.child;
        }
        Node<T> newNode = new Node<T>(v, prevNode.child);
        prevNode.child = newNode;
        return true;
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
