package Proyecto2EstructurasDeDatos.models;

/**
 * Clase resumen
 */
public class Research {
    public String title;
    public String body;
    public String[] authors;
    public String[] keywords;

    public Research(String title, String body, String[] authors, String[] keywords) {
        this.title = title;
        this.body = body;
        this.authors = authors;
        this.keywords = keywords;
    }
}