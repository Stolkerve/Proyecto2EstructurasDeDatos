package com.proyecto2estructurasdedatos;

import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIDefaults;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.proyecto2estructurasdedatos.containers.HashMap;
import com.proyecto2estructurasdedatos.gui.AssetsLoader;
import com.proyecto2estructurasdedatos.gui.MainPanel;
import com.proyecto2estructurasdedatos.utils.AssetsManager;

class Prueba {
    String titulo;
    String body;

    public Prueba(String titulo, String body) {
        this.titulo = titulo;
        this.body = body;
    }

    @Override
    public int hashCode() {
        return body.hashCode() | titulo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Prueba o = (Prueba) obj;
        return o.body.equals(body) && o.titulo.equals(titulo);
    }
}

/**
 *
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
        // HashMap<String, Integer> map = new HashMap<>();
        // map.insert("Lunes", 0);
        // map.insert("Martes", 1);
        // map.insert("Miercoles", 2);
        // map.insert("Jueves", 3);
        // map.insert("Viernes", 4);
        // map.insert("Sabado", 5);
        // map.insert("Domingo", 6);

        // for (var pair : map) {
        //     System.out.println("(" + pair.first + ", " + pair.secound + ")");
        // }

        // HashMap<Prueba, Integer> map2 = new HashMap<>();
        // map2.insert(new Prueba("Titulos", "Body"), 10);
        // map2.insert(new Prueba("Titulos", "Body"), 20);

        // for (var pair : map2) {
        //     System.out.println("(" + pair.first + ", " + pair.secound + ")");
        // }

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            // UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            Enumeration<Object> keys = defaults.keys();
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
