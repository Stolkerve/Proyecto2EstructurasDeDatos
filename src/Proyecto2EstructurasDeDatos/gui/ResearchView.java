package Proyecto2EstructurasDeDatos.gui;

import javax.swing.JTextPane;

import Proyecto2EstructurasDeDatos.models.Research;

/**
 *@author sebas
 */
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
        for (var a : r.authors) {
            if (i == r.authors.length - 1) {
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
