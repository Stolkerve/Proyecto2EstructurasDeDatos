package Proyecto2EstructurasDeDatos.utils;

import Proyecto2EstructurasDeDatos.containers.HashMap;
import Proyecto2EstructurasDeDatos.containers.List;
import Proyecto2EstructurasDeDatos.models.Research;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
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

public class LoadFile {
    public static void saveState(HashMap<String, Research> map) {

        if (map.size() == 0) return;
        String msg = "Nombre del archivo de los resumenes cargados";
        String name = JOptionPane.showInputDialog(null, msg, "Guardar el estado", JOptionPane.DEFAULT_OPTION);
        if (name == null) return;
        while (name.length() == 0) {
            name = JOptionPane.showInputDialog(null, msg);
            if (name == null) return;
        }

        JFileChooser fileDialog = new JFileChooser("./src/Proyecto2EstructurasDeDatos/assets", FileSystemView.getFileSystemView());
        fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = fileDialog.showOpenDialog(null);
        if (res != JFileChooser.CANCEL_OPTION) {
            var path = fileDialog.getSelectedFile().getPath();
            File outputFile = new File(path + "/" + name + ".txt");
            if (outputFile.exists()) {
                int r = JOptionPane.showConfirmDialog(fileDialog, "El archivo ya existe, deseas sobre escribirlo?", "ERROR", JOptionPane.WARNING_MESSAGE);
                if (r != JOptionPane.OK_OPTION)
                    return;
            }
            try (FileWriter output = new FileWriter(outputFile)) {
                map.forEach((p, i) -> {
                    var ref = new Object() {
                        String researchs = "";
                    };
                    var r = p.secound;
                    ref.researchs += r.title + '\n';
                    ref.researchs += "Autores\n";
                    r.authors.forEach(a -> {
                        ref.researchs += a + '\n';
                        return null;
                    });
                    ref.researchs += "Resumen\n";
                    ref.researchs += r.body + '\n';
                    var ref1 = new Object() {
                        String keywords = "";
                        int i = 0;
                    };
                    r.keywords.forEach(k -> {
                        if (ref1.i == r.keywords.size()) {
                            ref1.keywords += k;
                            return null;
                        }
                        ref1.keywords += k + ", ";
                        ref1.i++;
                        return null;
                    });
                    ref.researchs += "Palabras claves: " + ref1.keywords + '\n';
                    try {
                        output.write(ref.researchs);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean loadFile(File file, HashMap<String, Research> map, boolean msg) {
        try {
            Scanner scanner = new Scanner(file);
            FileState state = FileState.OnTitle;

            var acceptedNames = "";
            var refusedNames = "";

            var title = "";
            var body = "";
            var authors = new List<String>();
            var keywords = new List<String>();

            var keywordsPatter = "(?i)(Palabras claves:).+";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (state) {
                    case OnTitle:
                        if (map.find(line) != null) {
                            refusedNames += "- " + title + '\n';
                            state = FileState.OnFindNextResearch;
                            continue;
                        }
                        if (line.equals("Autores")) {
                            state = FileState.OnAuthors;
                            continue;
                        }
                        title = line;
                        acceptedNames += "- " + title + '\n';
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

            if (msg) {
                if (refusedNames.length() != 0) {
                    JOptionPane.showMessageDialog(null,
                            "Ya existen los resumenes:\n" + refusedNames, "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                JOptionPane.showMessageDialog(null,
                        "Se Añadieron los resumenes exitosamente:\n" + acceptedNames, "Resumenes cargados",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo abrir archivo " + file.getPath(), "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
