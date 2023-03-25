package com.proyecto2estructurasdedatos.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.proyecto2estructurasdedatos.MainFrame;
import com.proyecto2estructurasdedatos.containers.HashMap;
import com.proyecto2estructurasdedatos.containers.List;
import com.proyecto2estructurasdedatos.models.Research;
import com.proyecto2estructurasdedatos.utils.AssetsManager;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

/**
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    MainFrame mainFrame;
    HashMap<String, Research> researchsMap;

    /**
     * Crear el panel principal, instancia los componentes del menu principal
     * 
     * @param mainFrame es la instancia del JFrame padre
     */
    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        researchsMap = new HashMap<>((v) -> {
            int fnvPrime = 0x811C9DC5;
            int hash = 0;
            for (var c : v.toLowerCase().getBytes()) {
                hash *= fnvPrime;
                hash ^= c;
            }
            return hash;
        }, (v1, v2) -> {
            return v1.equalsIgnoreCase(v2);
        });
        loadFile("./src/main/java/com/proyecto2estructurasdedatos/assets/resumenes.json", researchsMap);

        this.mainFrame.addWindowListener(new WindowAdapter() {
           @Override 
           public void windowClosing(WindowEvent windowEvent) {
            saveState();
           }
        });

        initComponents();
    }

    /**
     * Iniciar componentes
     */
    public void initComponents() {
        this.setLayout(new GridLayout());

        var menuBtns = new List<JButton>();

        var loadResearchBtn = new JButton("Añadir resumenes");

        Function<Boolean, Void> activateMenu = (v -> {
            if (v) {
                for (JButton btn : menuBtns)
                    btn.setEnabled(true);
            } else { // no cargo los resumenes por defecto
                for (JButton btn : menuBtns)
                    btn.setEnabled(false);
                loadResearchBtn.setEnabled(true);
            }
            return null;
        });

        loadResearchBtn.addActionListener(e -> {
            this.addMenuComponent(new LoadResearchMenu(this, researchsMap, "Resumenes", activateMenu));
        });

        var analyzeResearchBtn = new JButton("Analizar resumenes");
        analyzeResearchBtn.addActionListener(e -> {
            this.addMenuComponent(new AnalyzeResearchMenu(this, researchsMap, "Analizar resumenes"));
        });

        var searchResearchByKeywordBtn = new JButton("Buscar resumen por palabra clave");
        searchResearchByKeywordBtn.addActionListener(e -> {
            this.addMenuComponent(new SearchByKeywordMenu(this, researchsMap, "Buscar resumen por palabra clave"));
        });

        var searchResearchByAuthorBtn = new JButton("Buscar resumen por autor");
        searchResearchByAuthorBtn.addActionListener(e -> {
            this.addMenuComponent(new SearchByAuthorMenu(this, researchsMap, "Buscar resumen por autor"));
        });

        var quitBtn = new JButton("Salir");
        quitBtn.addActionListener(e -> {
            saveState();
            this.mainFrame.dispose();
            System.exit(0);
        });

        menuBtns.pushBack(new JButton[] {
                loadResearchBtn, analyzeResearchBtn, searchResearchByKeywordBtn,
                searchResearchByAuthorBtn, quitBtn
        });

        activateMenu.apply(researchsMap.size() != 0);

        var colsPanel = new JPanel(new GridBagLayout());
        var c = new GridBagConstraints();

        var leftCol = new JPanel(new GridLayout(3, 0));
        var centerCol = new JPanel(new GridLayout(menuBtns.size() + 1, 0, 0, 10));
        var rightCol = new JPanel(new GridLayout(3, 0));

        var catKiss = AssetsManager.getInstance().getImage("cat-kiss");
        if (catKiss != null) {
            for (int i = 0; i < 3; i++) {
                var imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                leftCol.add(imgPanel);
            }

            for (int i = 0; i < 3; i++) {
                var imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                rightCol.add(imgPanel);
            }
        }

        for (var btn : menuBtns)
            centerCol.add(btn);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.ipadx = 40;
        colsPanel.add(leftCol, c);
        c.gridx = 1;
        c.ipadx = 40;
        colsPanel.add(centerCol, c);
        c.gridx = 2;
        colsPanel.add(rightCol, c);

        this.add(colsPanel);
    }

    /**
     * @param Menu insertar un nuevo menu
     */
    private void addMenuComponent(MenuComponent c) {
        this.removeAll();
        this.add(c);
        this.repaint();
        this.validate();
    }

    /**
     * Metodo para cargar el archivo
     * 
     * @param file Direccion del archivo
     * @return HashMap con todos los resumenes
     */
    private void loadFile(String path, HashMap<String, Research> map) {
        try {
            var str = Files.readString(Path.of(path));
            var gson = new Gson();
            Research[] researchs = gson.fromJson(str, Research[].class);
            for (var r : researchs) {
                map.insert(r.title, r);
            }
        } catch (Exception e) {
            
        }
    }

    private void saveState() {
        if (researchsMap.size() == 0) return;
        String msg = "Nombre del archivo de los resumenes cargados";
        String name = JOptionPane.showInputDialog(null, msg, "Guardar el estado", JOptionPane.DEFAULT_OPTION);
        if (name == null) return;
        while (name.length() == 0) {
            name = JOptionPane.showInputDialog(null, msg);
            if (name == null) return;
        }

        JFileChooser fileDialog = new JFileChooser("./", FileSystemView.getFileSystemView());
        fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = fileDialog.showOpenDialog(null);
        if (res != JFileChooser.CANCEL_OPTION) {
            File outputFile = new File(name + ".json");
            if (outputFile.exists()) {
                int r = JOptionPane.showConfirmDialog(fileDialog, "El archivo ya existe, deseas sobre escribirlo?", "ERROR", JOptionPane.WARNING_MESSAGE);
                if (r != JOptionPane.OK_OPTION)
                    return;
            }
            try (FileWriter output = new FileWriter(outputFile)) {
                var jsonArr = new JsonArray();
                var gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                for (var p : researchsMap) {
                    var research = p.secound;
                    jsonArr.add(gson.toJsonTree(research));
                }
                output.write(gson.toJson(jsonArr));
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
