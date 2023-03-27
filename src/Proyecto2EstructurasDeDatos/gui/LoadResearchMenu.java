package Proyecto2EstructurasDeDatos.gui;

import Proyecto2EstructurasDeDatos.containers.HashMap;
import Proyecto2EstructurasDeDatos.models.Research;
import Proyecto2EstructurasDeDatos.utils.LoadFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.function.Function;

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

        JFileChooser fileDialog = new JFileChooser("./src/Proyecto2EstructurasDeDatos/assets", FileSystemView.getFileSystemView());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setDialogTitle("Selectciona el archivo");
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Solo archivos .txt", "txt"));
        var res = fileDialog.showOpenDialog(null);
        if (res != JFileChooser.CANCEL_OPTION) {
            researchFile = fileDialog.getSelectedFile();
            initComponents();
        }
    }

    private void initComponents() {
        var tempMap = new HashMap<String, Research>();
        failParse = !LoadFile.loadFile(researchFile, tempMap, false);

        var c = new GridBagConstraints();
        var panel = new JPanel(new GridBagLayout());
        var listModel = new DefaultListModel<String>();
        var list = new JList<String>(listModel);
        tempMap.forEach((pair, i) -> {
            listModel.addElement(pair.secound.title);
            return null;
        });

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        var sp = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        var rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        var btn = new JButton("Añadir resumenes");
        btn.addActionListener(e -> {
            var titles = list.getSelectedValuesList();
            var ref = new Object() {
                String acceptedNames = "";
                String refusedNames = "";
            };
            var counter = 0;
            if (titles.size() != 0) {
                for (var t : titles) {
                    var r = tempMap.find(t).secound;
                    if (researchsMap.find(t) == null) {
                        researchsMap.insert(t, r);
                        ref.acceptedNames += "- " + r.title + '\n';
                        continue;
                    }
                    ref.refusedNames += "- " + r.title + '\n';
                }

                if (ref.refusedNames.length() != 0) {
                    JOptionPane.showMessageDialog(null,
                            "Ya existen los resumenes:\n" + ref.refusedNames, "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
                // Se cargo un resumen por lo menos
                if (ref.acceptedNames.length() != 0)
                    JOptionPane.showMessageDialog(null,
                            "Se Añadieron los resumenes exitosamente:\n" + ref.acceptedNames, "Todo bien",
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
