package com.proyecto2estructurasdedatos.models;

import com.proyecto2estructurasdedatos.containers.List;

public class Research {
    public String title;
    public String body;
    public List<String> Authors;
    public List<String> keywords;

    public Research(String title, String body, List<String> authors, List<String> keywords) {
        this.title = title;
        this.body = body;
        Authors = authors;
        this.keywords = keywords;
    }
}