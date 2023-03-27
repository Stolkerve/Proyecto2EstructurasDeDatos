package com.proyecto2estructurasdedatos.containers;

/**
 * @author sebas
 * @param <T> Tipo de dato de val
 * @author sebas
 */
public class Node<T> {
    T val;
    Node<T> child;

    Node(T v, Node<T> child) {
        this.val = v;
        this.child = child;
    }
}
