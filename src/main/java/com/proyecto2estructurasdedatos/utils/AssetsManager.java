package com.proyecto2estructurasdedatos.utils;

import com.proyecto2estructurasdedatos.containers.List;

/**
 * Singleton que contiene todos los recursos usados por el programa
 * 
 * @author sebas
 */
public class AssetsManager {
    private static AssetsManager instance;
    private List<ImageAsset> imageAssets;

    AssetsManager() {
        this.imageAssets = new List<>();
    }

    public static AssetsManager getInstance() {
        if (instance == null) {
            synchronized (AssetsManager.class) {
                if (instance == null)
                    instance = new AssetsManager();
            }
        }
        return instance;
    }

    /**
     * @param path Direccion relativa al directorio del proyecto
     * @param name Nombre del recurso
     * @return Retorna el recurso agregado
     */
    public ImageAsset addImage(String path, String name) {
        var newAsset = new ImageAsset(path, name);
        if (newAsset.image.getIconWidth() > 0)
            this.imageAssets.pushBack(newAsset);
        else
            return null;
        return newAsset;
    }

    /**
     * @param name Nombre del recurso a buscar
     * @return Retorna el recurso se si encontro, de lo contrario Null
     */
    public ImageAsset getImage(String name) {
        for (var a : this.imageAssets) {
            if (a.name.equals(name))
                return a;
        }
        return null;
    }
}
