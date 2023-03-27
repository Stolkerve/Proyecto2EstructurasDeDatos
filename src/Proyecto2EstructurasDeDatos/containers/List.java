package Proyecto2EstructurasDeDatos.containers;

import java.util.function.Function;

/**
 * Contenedor de un elementos enlazados
 *
 * @author sebas
 */
public class List<T> {
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
    public T forEach(Function<T, T> callback) {
        Node<T> current = this.begin;
        while (current != null) {
            T data = current.val;
            current = current.child;
            T ret = callback.apply(data);
            if (ret != null) return ret;
        }
        return null;
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