package Proyecto2EstructurasDeDatos.gui;

import Proyecto2EstructurasDeDatos.MainFrame;
import Proyecto2EstructurasDeDatos.containers.HashMap;
import Proyecto2EstructurasDeDatos.containers.List;
import Proyecto2EstructurasDeDatos.models.Research;
import Proyecto2EstructurasDeDatos.utils.AssetsManager;
import Proyecto2EstructurasDeDatos.utils.LoadFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.function.Function;

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
        loadFile("./src/Proyecto2EstructurasDeDatos/assets/resumenes.txt", researchsMap);

        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                LoadFile.saveState(researchsMap);
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
                menuBtns.forEach(btn -> {
                    btn.setEnabled(true);
                    return null;
                });
            } else { // no cargo los resumenes por defecto
                menuBtns.forEach(btn -> {
                    btn.setEnabled(false);
                    return null;
                });
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
            LoadFile.saveState(researchsMap);
            this.mainFrame.dispose();
            System.exit(0);
        });

        menuBtns.pushBack(new JButton[]{
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

        menuBtns.forEach(btn -> {
            centerCol.add(btn);
            return null;
        });

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
     * @param c insertar un nuevo menu
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
     * @param path Direccion del archivo
     * @return HashMap con todos los resumenes
     */
    private void loadFile(String path, HashMap<String, Research> map) {
        var file = new File(path);
        LoadFile.loadFile(file, researchsMap, true);
    }

}
