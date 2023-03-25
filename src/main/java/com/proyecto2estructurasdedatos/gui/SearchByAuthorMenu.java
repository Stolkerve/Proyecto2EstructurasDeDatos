package com.proyecto2estructurasdedatos.gui;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import com.proyecto2estructurasdedatos.containers.HashMap;
import com.proyecto2estructurasdedatos.containers.List;
import com.proyecto2estructurasdedatos.containers.Pair;
import com.proyecto2estructurasdedatos.models.Research;

/**
 * @author sebas
 */
public class SearchByAuthorMenu extends MenuComponent {
    DefaultListModel<String> listModel;
    List<Pair<String, Research>> authorsList;
    ResearchView researchView;
    JScrollPane scrollResearchView;

    /**
     * @param mainMenuPanel Menu principal
     * @param researchsMap  HashMap con los resumenes
     * @param title         Titulo del menu
     */
    public SearchByAuthorMenu(MainPanel mainMenuPanel, HashMap<String, Research> researchsMap, String title) {
        super(mainMenuPanel, researchsMap, title);
        listModel = new DefaultListModel<>();
        authorsList = new List<>();
        researchView = new ResearchView();
        scrollResearchView = new JScrollPane(researchView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        for (var p : researchsMap) {
            var r = p.secound;
            for (var a : r.authors) {
                authorsList.pushBack(new Pair<>(a, r));
            }
        }

        initMenuComponents();
    }

    @Override
    protected void initMenuComponents() {
        var c = new GridBagConstraints();
        var panel = new JPanel(new GridBagLayout());

        var c2 = new GridBagConstraints();
        var researchPanel = new JPanel(new GridBagLayout());

        var inputPanel = new JPanel(new BorderLayout());
        var authorsInput = new JTextField();
        authorsInput.addActionListener(e -> {
            var authorText = authorsInput.getText();
            if (authorText.length() != 0) {
                listModel.clear();
                researchView.setText("<span></span>");
                for (var p : authorsList) {
                    if (p.first.matches(String.format("(?i).*\\b%s\\b.*", authorText))) {
                        listModel.addElement(p.secound.title);
                    }
                }
                if (listModel.size() != 0)
                    return;
                JOptionPane.showMessageDialog(this,
                        "No se encontro el autor " + authorText, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        var inputlabel = new JLabel("Ingrese un autor");
        inputlabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        inputPanel.add(inputlabel, BorderLayout.WEST);
        inputPanel.add(authorsInput, BorderLayout.CENTER);

        c2.gridx = 0;
        c2.gridy = 1;
        c2.weighty = 1.0;
        c2.weightx = 0.1;
        c2.fill = GridBagConstraints.BOTH;
        researchPanel.add(this.createResearchsList(), c2);

        c2.gridx = 1;
        c2.gridy = 1;
        c2.weightx = 0.9;
        c2.fill = GridBagConstraints.BOTH;
        researchPanel.add(this.createResearchDisplay(), c2);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(inputPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panel.add(researchPanel, c);

        this.add(panel);
    }

    private JPanel createResearchsList() {
        var panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var c2 = new GridBagConstraints();

        var titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Resumenes"));
        var listPanel = new JPanel(new GridLayout());

        var list = new CustomList(listModel, 100);
        list.addListSelectionListener(e -> {
            var t = list.getSelectedValue();
            if (t != null) {
                var r = researchsMap.find(t).secound;
                researchView.setResearch(r);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        scrollResearchView.getVerticalScrollBar().setValue(0);
                    }
                });
            }
        });

        JScrollPane sp = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listPanel.add(sp);

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.gridy = 0;
        c2.weightx = 1.0;
        panel.add(titlePanel, c2);
        c2.fill = GridBagConstraints.BOTH;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 1.0;

        panel.add(listPanel, c2);

        return panel;
    }

    private JPanel createResearchDisplay() {
        var panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var c2 = new GridBagConstraints();

        var titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Resumen"));
        var listPanel = new JPanel(new GridLayout());

        listPanel.add(scrollResearchView);

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.gridy = 0;
        c2.weightx = 1.0;
        panel.add(titlePanel, c2);
        c2.fill = GridBagConstraints.BOTH;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 1.0;

        panel.add(listPanel, c2);

        return panel;
    }

}
