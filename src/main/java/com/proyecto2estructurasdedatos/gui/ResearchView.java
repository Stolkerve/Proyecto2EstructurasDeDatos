package com.proyecto2estructurasdedatos.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;

import com.proyecto2estructurasdedatos.models.Research;

public class ResearchView extends JTextPane {
    public ResearchView() {
        this.setContentType("text/html");
    }

    public void setResearch(Research r) {
        var titleText = String.format(
                "<h3 style=\"text-align: center\">%s</h3>",
                r.title);
        int i = 0;
        var authors = "";
        for (var a : r.Authors) {
            if (i == r.Authors.size() - 1) {
                authors += a + ".";
                break;
            }
            authors += a + ", ";
            i++;
        }
        var authorsText = String.format(
                "<p><b>Autores</b>: %s</p>", authors);

        var bodyText = String.format("<p><b>Resumen</b></p><p>%s</p>", r.body);

        var keywordsText = "<p><b>Palabras claves</b></p><ul style=\"margin-left: 10px; list-style-position: inside\">";
        for (var k : r.keywords) {
            keywordsText += String.format("<li><b>%s</b></li>", k);
        }
        keywordsText += "</ul>";

        setText(titleText + authorsText + bodyText + keywordsText);
    }

}