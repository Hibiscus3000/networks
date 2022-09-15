package ru.nsu.fit.nat.lab1.udp.table;

import javax.swing.table.AbstractTableModel;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;

public class ReceiverTableModel extends AbstractTableModel {

    private final Map<Integer,InetSocketAddress> intAddrMap;
    private final Map<InetSocketAddress, Integer> addrIntMap;
    private final Map<Integer, Date> dateMap;
    String[] columnNames = {"id", "IP","Port", "Time"};

    public ReceiverTableModel(Map<InetSocketAddress, Integer> addrIntMap, Map<Integer, InetSocketAddress> intAddrMap,
                              Map<Integer, Date> dateMap) {
        this.addrIntMap = addrIntMap;
        this.intAddrMap = intAddrMap;
        this.dateMap = dateMap;
    }

    @Override
    public int getRowCount() {
        return intAddrMap.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (0 == columnIndex)
            return rowIndex + 1;
        if (1 == columnIndex)
            return intAddrMap.get(rowIndex).getAddress().toString();
        if (2 == columnIndex)
            return intAddrMap.get(rowIndex).getPort();
        return dateMap.get(rowIndex).toString();
    }

    @Override
    public String getColumnName(int c) {
        return columnNames[c];
    }

}
