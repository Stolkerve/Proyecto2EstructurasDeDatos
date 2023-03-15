package com.proyecto2estructurasdedatos.gui;

import com.proyecto2estructurasdedatos.MainFrame;
import com.proyecto2estructurasdedatos.containers.HashMap;
import com.proyecto2estructurasdedatos.containers.List;
import com.proyecto2estructurasdedatos.models.Research;
import com.proyecto2estructurasdedatos.utils.AssetsManager;
import com.proyecto2estructurasdedatos.utils.LoadFileDialog;

import java.awt.*;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

        researchsMap = LoadFileDialog.loadFile(new File("./resumenes.txt"));

        initComponents();
    }

    /**
     * Iniciar componentes
     */
    public void initComponents() {
        this.setLayout(new GridLayout());

        var menuBtns = new List<JButton>();

        var loadResearchBtn = new JButton("Cargar resumenes");
        loadResearchBtn.addActionListener(e -> {
            researchsMap = LoadFileDialog.loadFileDialog();
            if (researchsMap != null) {
                for (JButton btn : menuBtns)
                    btn.setEnabled(true);
            } else {
                for (JButton btn : menuBtns)
                    btn.setEnabled(false);
                loadResearchBtn.setEnabled(true);
            }
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
            this.mainFrame.dispose();
        });

        var helpBtn = new JButton("Super C U B O");
        helpBtn.addActionListener(e -> {
            new Cube();
        });

        menuBtns.pushBack(new JButton[] {
                loadResearchBtn, analyzeResearchBtn, searchResearchByKeywordBtn,
                searchResearchByAuthorBtn, quitBtn
        });

        if (researchsMap != null) {
            for (JButton btn : menuBtns)
                btn.setEnabled(true);
        } else {
            for (JButton btn : menuBtns)
                btn.setEnabled(false);
            loadResearchBtn.setEnabled(true);
        }

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
        centerCol.add(helpBtn);

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
}
