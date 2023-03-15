package com.proyecto2estructurasdedatos.gui;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Color;

public class CustomList extends JList<String> {

    public CustomList(DefaultListModel<String> dataModel, int size) {
        super(dataModel);
        this.setVisibleRowCount(0);
        this.setCellRenderer(new MyCellRenderer(260));
    }
}

/**
 * @author sebas
 */
class MyCellRenderer extends DefaultListCellRenderer {
    public static final String HTML_1 = "<html><body style=' width: ";
    public static final String HTML_2 = "px; overflow-wrap: break-word'>";
    public static final String HTML_3 = "</html>";
    private int width;

    public MyCellRenderer(int width) {
        this.width = width;
    }

    @Override
    public JLabel getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        String text = HTML_1 + String.valueOf(width) + HTML_2 + value.toString()
                + HTML_3;
        JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, text, index, isSelected,
                cellHasFocus);
        listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        return listCellRendererComponent;
    }

}