package Proyecto2EstructurasDeDatos.gui;

import Proyecto2EstructurasDeDatos.containers.HashMap;
import Proyecto2EstructurasDeDatos.models.Research;
import Proyecto2EstructurasDeDatos.utils.AssetsManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author sebas
 */
public class MenuComponent extends JPanel {
    HashMap<String, Research> researchsMap;
    MainPanel mainMenuPanel;

    /**
     * Constructor que crea un topbar en el menu
     *
     * @param mainMenuPanel Panel principal
     * @param title         titulo del menu
     */
    protected MenuComponent(MainPanel mainMenuPanel, HashMap<String, Research> researchsMap, String title) {
        this.mainMenuPanel = mainMenuPanel;
        this.researchsMap = researchsMap;
        this.initSize();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(2, 2, 2, 2));
        var leftArrow = AssetsManager.getInstance().getImage("left-arrow");
        var backToMenuBtn = new JButton(leftArrow.image);
        backToMenuBtn.addActionListener(e -> this.backToMainMenu());
        var topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(backToMenuBtn);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(new JLabel(title));
        topPanel.add(Box.createHorizontalGlue());

        this.add(topPanel);

    }

    /**
     * Constructor basico
     *
     * @param mainMenuPanel Panel principal
     */
    protected MenuComponent(MainPanel mainMenuPanel, HashMap<String, Research> researchsMap) {
        this.mainMenuPanel = mainMenuPanel;
        this.researchsMap = researchsMap;
        this.initSize();
    }

    /**
     * Interfaz para inicar los componentes del menu
     */
    protected void initMenuComponents() {
    }

    /**
     * Volver al menu principal
     */
    protected void backToMainMenu() {
        this.mainMenuPanel.removeAll();
        this.mainMenuPanel.initComponents();
        this.mainMenuPanel.repaint();
        this.mainMenuPanel.validate();
    }

    /**
     * Forzar las dimenciones del menu
     */
    private void initSize() {
        var d = this.mainMenuPanel.mainFrame.getSize();
        this.setPreferredSize(new Dimension(d.width, d.height));
        this.setSize(new Dimension(d.width, d.height));
        this.setMaximumSize(new Dimension(d.width, d.height));
        this.setMinimumSize(new Dimension(d.width, d.height));
    }
}
