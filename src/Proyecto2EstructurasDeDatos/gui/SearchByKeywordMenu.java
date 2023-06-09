package Proyecto2EstructurasDeDatos.gui;

import Proyecto2EstructurasDeDatos.containers.HashMap;
import Proyecto2EstructurasDeDatos.containers.List;
import Proyecto2EstructurasDeDatos.models.Research;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author sebas
 */
public class SearchByKeywordMenu extends MenuComponent {
    DefaultListModel<String> listModel;
    HashMap<String, List<Research>> keywordsMap;
    ResearchView researchView;
    JScrollPane scrollResearchView;

    /**
     * @param mainMenuPanel Menu principal
     * @param researchsMap  HashMap con los resumenes
     * @param title         Titulo del menu
     */
    public SearchByKeywordMenu(MainPanel mainMenuPanel, HashMap<String, Research> researchsMap, String title) {
        super(mainMenuPanel, researchsMap, title);
        listModel = new DefaultListModel<>();
        keywordsMap = new HashMap<>((v) -> {
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
        researchView = new ResearchView();
        scrollResearchView = new JScrollPane(researchView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        researchsMap.forEach((p, i) -> {
            var r = p.secound;
            r.keywords.forEach(k -> {
                var researchs = keywordsMap.find(k);
                if (researchs != null) {
                    researchs.secound.pushBack(r);
                    return null;
                }
                var l = new List<Research>();
                l.pushBack(r);
                keywordsMap.insert(k, l);
                return null;
            });
            return null;
        });

        initMenuComponents();
    }

    @Override
    protected void initMenuComponents() {
        var c = new GridBagConstraints();
        var panel = new JPanel(new GridBagLayout());

        var c2 = new GridBagConstraints();
        var researchPanel = new JPanel(new GridBagLayout());

        var inputPanel = new JPanel(new BorderLayout());
        var keywordInput = new JTextField();
        keywordInput.addActionListener(e -> {
            var keywordText = keywordInput.getText();
            if (keywordText.length() != 0) {
                listModel.clear();
                researchView.setText("<span></span>");
                var p = keywordsMap.find(keywordText);
                if (p != null) {
                    p.secound.forEach(r -> {
                        listModel.addElement(r.title);
                        return null;
                    });
                    return;
                }
                JOptionPane.showMessageDialog(this,
                        "No existe la palabra clave " + keywordText, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        var inputlabel = new JLabel("Ingrese una palabra clave");
        inputlabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        inputPanel.add(inputlabel, BorderLayout.WEST);
        inputPanel.add(keywordInput, BorderLayout.CENTER);

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

        var sp = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
