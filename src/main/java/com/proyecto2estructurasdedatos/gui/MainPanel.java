package com.proyecto2estructurasdedatos.gui;

import com.proyecto2estructurasdedatos.MainFrame;
import com.proyecto2estructurasdedatos.containers.List;
import com.proyecto2estructurasdedatos.utils.AssetsManager;
import com.proyecto2estructurasdedatos.utils.ImageAsset;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    MainFrame mainFrame;

    /**
     * Crear el panel principal, instancia los componentes del menu principal
     * 
     * @param mainFrame es la instancia del JFrame padre
     */
    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
    }

    /**
     * Iniciar componentes
     */
    public void initComponents() {
        this.setLayout(new GridLayout());

        List<JButton> menuBtns = new List<JButton>();

        JButton loadResearchBtn = new JButton("Cargar resumenes");
        loadResearchBtn.addActionListener(e -> {
        });

        JButton analyzeResearchBtn = new JButton("Analizar resumenes");
        analyzeResearchBtn.addActionListener(e -> {
        });

        JButton searchResearchByKeywordBtn = new JButton("Buscar Investigaciones por palabra clave");
        searchResearchByKeywordBtn.addActionListener(e -> {
        });

        JButton searchResearchByAuthorBtn = new JButton("Buscar Investigaciones por Autor");
        searchResearchByAuthorBtn.addActionListener(e -> {
        });

        JButton quitBtn = new JButton("Salir");
        quitBtn.addActionListener(e -> {
        });

        JButton helpBtn = new JButton(";)");
        helpBtn.addActionListener(e -> {
        });

        menuBtns.pushBack(new JButton[] {
                loadResearchBtn, analyzeResearchBtn, searchResearchByKeywordBtn,
                searchResearchByAuthorBtn, quitBtn
        });

        // if (!graph.init) {
        // for (JButton btn : menuBtns)
        // btn.setEnabled(false);
        // loadGraphBtn.setEnabled(true);
        //

        JPanel colsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel leftCol = new JPanel(new GridLayout(3, 0));
        JPanel centerCol = new JPanel(new GridLayout(menuBtns.size() + 1, 0, 0, 20));
        JPanel rightCol = new JPanel(new GridLayout(3, 0));

        ImageAsset catKiss = AssetsManager.getInstance().getImage("cat-kiss");
        if (catKiss != null) {
            for (int i = 0; i < 3; i++) {
                JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                leftCol.add(imgPanel);
            }

            for (int i = 0; i < 3; i++) {
                JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                rightCol.add(imgPanel);
            }
        }

        for (JButton btn : menuBtns)
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
     * @param Menu a insertar
     */
    void addMenuComponent(MenuComponent c) {
        this.removeAll();
        this.add(c);
        this.repaint();
        this.validate();
    }
}
