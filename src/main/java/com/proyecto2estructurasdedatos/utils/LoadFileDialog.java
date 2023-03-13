package com.proyecto2estructurasdedatos.utils;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.proyecto2estructurasdedatos.containers.HashMap;
import com.proyecto2estructurasdedatos.containers.List;
import com.proyecto2estructurasdedatos.models.Research;

/**
 * 
 * Estado de carga del arhcivo
 * 
 * @author sebas
 */
enum FileState {
    OnTitle,
    OnAuthors,
    OnBody,
    OnFindNextResearch,
};

/**
 * Clase con metodos estaticos para cargar archivos
 * 
 * @author sebas
 */
public class LoadFileDialog {

    /**
     * Metodo estatico para cargar el archivo con intefaz
     * @return HashMap con todos los resumenes
     */
    public static HashMap<String, Research> loadFileDialog() {
        JFileChooser fileDialog = new JFileChooser("./", FileSystemView.getFileSystemView());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setDialogTitle("Selectciona el archivo de guardado de almacenes");
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Solo archivos .txt", "txt"));
        int res = fileDialog.showOpenDialog(null);

        if (res != JFileChooser.CANCEL_OPTION) {
            return loadFile(fileDialog.getSelectedFile());
        }

        return null;
    }

    /**
     * Metodo estatico para cargar el archivo
     * @param file Direccion del archivo
     * @return HashMap con todos los resumenes
     */
    public static HashMap<String, Research> loadFile(File file) {
        var map = new HashMap<String, Research>();
        try {
            Scanner scanner = new Scanner(file);
            FileState state = FileState.OnTitle;

            String title = "";
            String body = "";
            List<String> authors = new List<>();
            List<String> keywords = new List<>();

            var keywordsPatter = "(?i)(Palabras claves:).+";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (state) {
                    case OnTitle:
                        if (map.find(line) != null) {
                            JOptionPane.showMessageDialog(null,
                                    "Ya existe el resumen " + line + " Se ignorara.", "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                            state = FileState.OnFindNextResearch;
                            continue;
                        }
                        if (line.equals("Autores")) {
                            state = FileState.OnAuthors;
                            continue;
                        }
                        title = line;
                        break;
                    case OnAuthors:
                        if (line.equals("Resumen")) {
                            state = FileState.OnBody;
                            continue;
                        }
                        authors.pushBack(line);
                        break;
                    case OnBody:
                        if (line.matches(keywordsPatter)) {
                            state = FileState.OnTitle;
                            keywords.pushBack(line.substring("Palabras claves: ".length()).replace(".", "").split(", "));
                            map.insert(title, new Research(title, body, authors, keywords));
                            title = "";
                            body = "";
                            authors = new List<>();
                            keywords = new List<>();
                            continue;
                        }
                        body += line;
                        break;
                    case OnFindNextResearch:
                        if (line.matches(keywordsPatter)) {
                            state = FileState.OnTitle;
                        }
                        break;
                }
            }
            scanner.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo abrir archivo " + file.getPath(), "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return map;
    }
}
