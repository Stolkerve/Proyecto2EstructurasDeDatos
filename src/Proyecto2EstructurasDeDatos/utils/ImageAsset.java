package Proyecto2EstructurasDeDatos.utils;

import javax.swing.*;

/**
 * @author sebas
 */
public class ImageAsset {
    String name;
    public ImageIcon image;

    /**
     * @param path Direccion relativa al directorio del proyecto
     * @param name Nombre del recurso
     */
    ImageAsset(String path, String name) {
        this.name = name;
        this.image = new ImageIcon(path);
    }
}
