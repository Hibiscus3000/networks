package ru.nsu.fit.nat.lab1.udp.table;

import javax.swing.*;
import java.awt.*;

public class ReceiverTable extends JTable {

    private final Font tableFont = new Font("Arial Rounded MT Bold", Font.PLAIN, 14);
    private final Dimension windowSize;
    private final int roomForId = 25;
    private final int roomForPort = 50;

    public ReceiverTable(ReceiverTableModel receiverTableModel, Dimension windowSize) {
        super(receiverTableModel);
        this.windowSize = windowSize;
        setFont(tableFont);
        getTableHeader().setFont(tableFont);
        setDefaultWidth();
    }

    public void update() {
        resizeAndRepaint();
    }

    private void setDefaultWidth() {
        getColumnModel().getColumn(0).setPreferredWidth(roomForId);
        getColumnModel().getColumn(1).setPreferredWidth((int) ((windowSize.getWidth() - roomForId) / 2));
        getColumnModel().getColumn(2).setPreferredWidth(roomForPort);
        getColumnModel().getColumn(3).setPreferredWidth((int) ((windowSize.getWidth() - roomForId) / 2));
    }

}
