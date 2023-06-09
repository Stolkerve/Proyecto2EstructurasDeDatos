package Proyecto2EstructurasDeDatos;

import Proyecto2EstructurasDeDatos.gui.AssetsLoader;
import Proyecto2EstructurasDeDatos.gui.MainPanel;
import Proyecto2EstructurasDeDatos.utils.AssetsManager;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

/**
 * @author sebas
 */
public class MainFrame extends javax.swing.JFrame {
    final int MIN_WIDTH = 960;
    final int MIN_HEIGHT = 720;

    MainPanel mainPanel;

    public MainFrame() {
        new AssetsLoader();

        this.mainPanel = new MainPanel(this);
        initComponents();
    }

    private void initComponents() {
        // Setear las dimenciones de la ventana
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        this.setPreferredSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        this.setSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        this.setResizable(false);

        var icon = AssetsManager.getInstance().getImage("amazon-icon");
        if (icon != null) {
            this.setIconImage(icon.image.getImage());
        }

        this.add(mainPanel);

        pack();

        this.setLocationRelativeTo(null);
        this.mainPanel.setMinimumSize(this.getSize());
        this.mainPanel.setMaximumSize(this.getSize());
        this.mainPanel.setPreferredSize(this.getSize());
        this.mainPanel.setSize(this.getSize());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            // UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            var keys = defaults.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                if ((key instanceof String) && (((String) key).endsWith(".font"))) {
                    FontUIResource font = (FontUIResource) UIManager.get(key);
                    defaults.put(key, new FontUIResource(font.getFontName(), font.getStyle(), 20));
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
