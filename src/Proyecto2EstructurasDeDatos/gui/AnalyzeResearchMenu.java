package Proyecto2EstructurasDeDatos.gui;

import Proyecto2EstructurasDeDatos.containers.HashMap;
import Proyecto2EstructurasDeDatos.models.Research;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sebas
 */
public class AnalyzeResearchMenu extends MenuComponent {
    JTextPane researchTextArea;

    /**
     * @param mainMenuPanel Menu principal
     * @param researchsMap  HashMap con los resumenes
     * @param title         Titulo del menu
     */
    public AnalyzeResearchMenu(MainPanel mainMenuPanel, HashMap<String, Research> researchsMap, String title) {
        super(mainMenuPanel, researchsMap, title);
        initMenuComponents();
    }

    @Override
    protected void initMenuComponents() {
        researchTextArea = new JTextPane();
        researchTextArea.setContentType("text/html");

        var c = new GridBagConstraints();
        var panel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panel.add(this.createResearchsList(), c);

        c.gridx = 1;
        c.weightx = 0.9;
        c.fill = GridBagConstraints.BOTH;
        panel.add(this.createResearchDisplay(), c);

        this.add(panel);
    }

    private JPanel createResearchsList() {
        var panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var c2 = new GridBagConstraints();

        var titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Resumenes"));
        var listPanel = new JPanel(new GridLayout());
        var listModel = new DefaultListModel<String>();

        {
            var titles = new String[researchsMap.size()];
            researchsMap.forEach((pair, i) -> {
                titles[i] = pair.first;
                return null;
            });
            Arrays.sort(titles);
            for (var t : titles)
                listModel.addElement(t);
        }

        var list = new CustomList(listModel, 100);
        list.addListSelectionListener(e -> {
            var research = researchsMap.find(list.getSelectedValue()).secound;
            var titleText = String.format(
                    "<h3 style=\"text-align: center\">%s</h3>",
                    research.title);
            var ref = new Object() {
                int i = 0;
                String authors = "";
            };
            research.authors.forEach(a -> {
                if (ref.i == research.authors.size() - 1) {
                    ref.authors += a + ".";
                    return "";
                }
                ref.authors += a + ", ";
                ref.i++;
                return null;
            });
            //for (var a : research.secound.authors) {
            //}
            var authorsText = String.format(
                    "<p><b>Autores</b>: %s</p>", ref.authors);

            var ref2 = new Object() {
                String keywordsText = "<ul style=\"margin-left: 10px; list-style-position: inside\">";
            };

            research.keywords.forEach(k -> {
                Pattern pattern = Pattern.compile(k, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(research.body);
                var matches = matcher.results().count();
                ref2.keywordsText += String.format("<li><b>%s</b>: tiene <b>%d</b> apariciones en el resumen.</li>", k,
                        matches);
                return null;
            });
            ref2.keywordsText += "</ul>";

            researchTextArea.setText(titleText + authorsText + ref2.keywordsText);
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
        titlePanel.add(new JLabel("Analisis"));
        var listPanel = new JPanel(new GridLayout());

        JScrollPane sp = new JScrollPane(researchTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
}
