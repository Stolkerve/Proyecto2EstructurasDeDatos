package com.proyecto2estructurasdedatos.gui;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import java.awt.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.proyecto2estructurasdedatos.containers.HashMap;
import com.proyecto2estructurasdedatos.models.Research;

/**
 * @author sebas
 */
public class LoadResearchMenu extends MenuComponent {
    Function<Boolean, Void> activateMenuFunc;
    File researchFile;
    boolean failParse;

    public LoadResearchMenu(MainPanel mainMenuPanel, HashMap<String, Research> researchsMap,
            String title,
            Function<Boolean, Void> activateMenuFunc) {
        super(mainMenuPanel, researchsMap, title);
        this.activateMenuFunc = activateMenuFunc;
        failParse = false;

        JFileChooser fileDialog = new JFileChooser("./src/main/java/com/proyecto2estructurasdedatos/assets/", FileSystemView.getFileSystemView());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setDialogTitle("Selectciona el archivo");
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Solo archivos .json", "json"));
        var res = fileDialog.showOpenDialog(null);
        if (res != JFileChooser.CANCEL_OPTION) {
            researchFile = fileDialog.getSelectedFile();
            initComponents();
        }
    }

    private void initComponents() {
        try {
            var str = Files.readString(Path.of(researchFile.getPath()));
            var gson = new Gson();
            Research[] researchs = gson.fromJson(str, Research[].class);

            var c = new GridBagConstraints();
            var panel = new JPanel(new GridBagLayout());
            var listModel = new DefaultListModel<String>();
            var list = new JList<String>(listModel);
            for (var r : researchs) {
                listModel.addElement(r.title);
            }

            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            var sp = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            var rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            var btn = new JButton("Añadir resumenes");
            btn.addActionListener(e -> {
                var indices = list.getSelectedIndices();
                var counter = 0;
                var names = "";
                if (indices.length != 0) {
                    for (var i : indices) {
                        var r = researchs[i];
                        if (researchsMap.find(r.title) == null) {
                            researchsMap.insert(r.title, r);
                            names += r.title + '\n';
                            continue;
                        }
                        JOptionPane.showMessageDialog(this,
                                "Ya existe el resumen " + r.title, "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                        counter++;
                    }

                    // Se cargo un resumen por lo menos
                    if (counter != indices.length)
                        JOptionPane.showMessageDialog(null,
                                "Se Añadieron los resumenes exitosamente\n" + names, "Todo bien",
                                JOptionPane.INFORMATION_MESSAGE);
                    activateMenuFunc.apply(true);
                    backToMainMenu();
                }
            });
            rightPanel.add(btn);
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1.0;
            panel.add(rightPanel, c);

            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 1;
            c.weightx = 1.0;
            c.weighty = 1.0;
            panel.add(sp, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 2;
            c.weightx = 1.0;
            c.weighty = 0.0;
            panel.add(new JLabel(
                    "<html><pre style='font-size:12px; color: white'>*Para seleccionar multiples resumenes, manten precionado Ctrl   ᕕ( ͡° ͜ʖ ͡° )ᕗ   ☀(▀U ▀-͠)</pre></html>"),
                    c);

            this.add(panel);
        } catch (Exception e) {
            failParse = true;
            JOptionPane.showMessageDialog(null,
                    "Ocurrio un error leyendo el archivo :(", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (researchFile == null || failParse) {
            activateMenuFunc.apply(false);
            backToMainMenu();
        }
    }
}
