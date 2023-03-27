package Proyecto2EstructurasDeDatos.containers;

/**
 * @param <T> First type
 * @param <U> Second type
 * @author sebas
 */
public class Pair<T, K> {
    public T first;
    public K secound;

    public Pair(T first, K secound) {
        this.first = first;
        this.secound = secound;
    }
}
