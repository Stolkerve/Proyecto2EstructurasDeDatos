package com.proyecto2estructurasdedatos;

import com.proyecto2estructurasdedatos.containers.HashMap;

class Prueba {
    String titulo;
    String body;
    public Prueba(String titulo, String body) {
        this.titulo = titulo;
        this.body = body;
    }

    @Override
    public int hashCode() {
        return body.hashCode() | titulo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Prueba o = (Prueba)obj;
        return o.body.equals(body) && o.titulo.equals(titulo);
    }
}

/**
 *
 * @author sebas
 */
public class MainFrame {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.insert("Lunes", 0);
        map.insert("Martes", 1);
        map.insert("Miercoles", 2);
        map.insert("Jueves", 3);
        map.insert("Viernes", 4);
        map.insert("Sabado", 5);
        map.insert("Domingo", 6);

        for (var pair : map) {
            System.out.println("(" + pair.first + ", " + pair.secound + ")");
        }
    
        HashMap<Prueba, Integer> map2 = new HashMap<>();
        map2.insert(new Prueba("Titulos", "Body"), 10);
        map2.insert(new Prueba("Titulos", "Body"), 20);

        for (var pair : map2) {
            System.out.println("(" + pair.first + ", " + pair.secound + ")");
        }
    }
}
