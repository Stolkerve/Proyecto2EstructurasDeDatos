package Proyecto2EstructurasDeDatos.gui;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.*;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import Proyecto2EstructurasDeDatos.containers.HashMap;
import Proyecto2EstructurasDeDatos.models.Research;

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
            var research = researchsMap.find(list.getSelectedValue());
            var titleText = String.format(
                    "<h3 style=\"text-align: center\">%s</h3>",
                    research.secound.title);
            int i = 0;
            var authors = "";
            for (var a : research.secound.authors) {
                if (i == research.secound.authors.length - 1) {
                    authors += a + ".";
                    break;
                }
                authors += a + ", ";
                i++;
            }
            var authorsText = String.format(
                    "<p><b>Autores</b>: %s</p>", authors);

            var keywordsText = "<ul style=\"margin-left: 10px; list-style-position: inside\">";
            for (var k : research.secound.keywords) {
                Pattern pattern = Pattern.compile(k);
                Matcher matcher = pattern.matcher(research.secound.body);
                var matches = matcher.results().count();
                keywordsText += String.format("<li><b>%s</b>: tiene <b>%d</b> apariciones en el resumen.</li>", k,
                        matches);
            }
            keywordsText += "</ul>";

            researchTextArea.setText(titleText + authorsText + keywordsText);
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
