package com.proyecto2estructurasdedatos.gui;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.*;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.proyecto2estructurasdedatos.containers.HashMap;
import com.proyecto2estructurasdedatos.models.Research;

/**
 * @author Sebas
 */
public class AnalyzeResearchMenu extends MenuComponent {
    JTextPane researchTextArea;

    /**
     * @param mainMenuPanel Menu principal
     * @param researchsMap HashMap con los resumenes
     * @param title Titulo del menu
     */
    public AnalyzeResearchMenu(MainPanel mainMenuPanel, HashMap<String, Research> researchsMap, String title) {
        super(mainMenuPanel, researchsMap, title);
    }

    @Override
    protected void initMenuComponents() {
        researchTextArea = new JTextPane();
        researchTextArea.setContentType("text/html");

        GridBagConstraints c = new GridBagConstraints();
        JPanel wearhouseProductsPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.weightx = 0.05;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        wearhouseProductsPanel.add(this.createResearchsList(), c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        wearhouseProductsPanel.add(this.createResearchDisplay(), c);

        this.add(wearhouseProductsPanel);

    }

    private Component createResearchsList() {
        var panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        // panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints c2 = new GridBagConstraints();

        var titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Resumenes"));
        var listPanel = new JPanel(new GridLayout());
        var listModel = new DefaultListModel<String>();
        
        {
            var titles = new String[researchsMap.size()];
            int i = 0;
            for (var pair : researchsMap) {
                titles[i] = pair.first;
                i++;
            }
            Arrays.sort(titles);
            for (var t : titles)
                listModel.addElement(t);
        }

        var list = new JList<String>(listModel);
        list.setVisibleRowCount(0);
        list.setCellRenderer(new MyCellRenderer(260));
        list.addListSelectionListener(e -> {
            var research = researchsMap.find(list.getSelectedValue());
            var titleText = String.format(
                "<h3 style=\"text-align: center\">%s</h3>",
                research.secound.title
            );
            int i = 0;
            var authors = "";
            for (var a : research.secound.Authors) {
                if (i == research.secound.Authors.size() - 1) {
                    authors += a + ".";
                    break;
                }
                authors += a + ", ";
                i++;
            }
            var authorsText = String.format(
                "<p><b>Autores</b>: %s</p>", authors
            );

            var keywordsText = "<ul style=\"margin-left: 10px; list-style-position: inside\">";
            for (var k : research.secound.keywords) {
                Pattern pattern = Pattern.compile(k);
                Matcher matcher = pattern.matcher(research.secound.body);
                var matches = matcher.results().count();
                keywordsText += String.format("<li><b>%s</b>: tiene <b>%d</b> apariciones en el resumen.</li>", k, matches);
            }
            keywordsText += "</ul>";
            
            researchTextArea.setText(titleText + authorsText + keywordsText);
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

    private Component createResearchDisplay() {
        var panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();

        var titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Analisis"));
        var listPanel = new JPanel(new GridLayout());

        JScrollPane sp = new JScrollPane(researchTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
}

/**
 * @author sebas
 */
class MyCellRenderer extends DefaultListCellRenderer {
    public static final String HTML_1 = "<html><body style=' width: ";
    public static final String HTML_2 = "px'>";
    public static final String HTML_3 = "</html>";
    private int width;

    public MyCellRenderer(int width) {
        this.width = width;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        String text = HTML_1 + String.valueOf(width) + HTML_2 + value.toString()
                + HTML_3;
        JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, text, index, isSelected,
                cellHasFocus);
        listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        return listCellRendererComponent;
    }

}