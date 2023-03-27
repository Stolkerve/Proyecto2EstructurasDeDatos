package Proyecto2EstructurasDeDatos.containers;

/**
 * @param <T> Tipo de dato de val
 * @author sebas
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
