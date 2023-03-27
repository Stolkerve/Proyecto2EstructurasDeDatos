package Proyecto2EstructurasDeDatos.models;

import Proyecto2EstructurasDeDatos.containers.List;

/**
 * Clase resumen
 */
public class Research {
    public String title;
    public String body;
    public List<String> authors;
    public List<String> keywords;

    public Research(String title, String body, List<String> authors, List<String> keywords) {
        this.title = title;
        this.body = body;
        this.authors = authors;
        this.keywords = keywords;
    }
}