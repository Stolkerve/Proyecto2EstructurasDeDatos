/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyecto2estructurasdedatos;

import com.proyecto2estructurasdedatos.containers.HashMap;

/**
 *
 * @author sebas
 */
public class Proyecto2EstructurasDeDatos {

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
    }
}
