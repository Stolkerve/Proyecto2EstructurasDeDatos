package Proyecto2EstructurasDeDatos.gui;

import Proyecto2EstructurasDeDatos.models.Research;

import javax.swing.*;

/**
 * @author sebas
 */
public class ResearchView extends JTextPane {
    public ResearchView() {
        this.setContentType("text/html");
    }

    public void setResearch(Research r) {
        var titleText = String.format(
                "<h3 style=\"text-align: center\">%s</h3>",
                r.title);
        var ref = new Object() {
            int i = 0;
            String authors = "";
        };
        r.authors.forEach(a -> {
            if (ref.i == r.authors.size() - 1) {
                ref.authors += a + ".";
                return ref.authors;
            }
            ref.authors += a + ", ";
            ref.i++;
            return null;
        });
        var authorsText = String.format(
                "<p><b>Autores</b>: %s</p>", ref.authors);

        var bodyText = String.format("<p><b>Resumen</b></p><p>%s</p>", r.body);

        var ref1 = new Object() {
            String keywordsText = "<p><b>Palabras claves</b></p><ul style=\"margin-left: 10px; list-style-position: inside\">";
        };
        r.keywords.forEach(k -> {
            ref1.keywordsText += String.format("<li><b>%s</b></li>", k);
            return null;
        });
        ref1.keywordsText += "</ul>";

        setText(titleText + authorsText + bodyText + ref1.keywordsText);
    }

}
