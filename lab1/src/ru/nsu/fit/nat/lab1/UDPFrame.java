package ru.nsu.fit.nat.lab1;

import ru.nsu.fit.nat.lab1.udp.Receiver;
import ru.nsu.fit.nat.lab1.udp.Sender;
import ru.nsu.fit.nat.lab1.udp.table.ReceiverTable;

import javax.swing.*;
import java.awt.*;

public class UDPFrame extends JFrame {

    private final double sizeScale = 0.5;
    private JTextField addressField;
    private JTextField portField;
    private final Font font = new Font("Arial Rounded MT Bold", Font.PLAIN, 16);
    private Sender sender;
    private Receiver receiver;
    private Thread senderThread;
    private Thread receiverThread;
    private ReceiverTable table;
    private Timer tableUpdateTimer;
    private Dimension windowSize;

    public UDPFrame(String address, String port) throws Exception {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        windowSize = new Dimension((int) (screenSize.getWidth() / 2), (int) (screenSize.getHeight() / 2));
        setSize((int) (sizeScale * screenSize.getWidth()), (int) (sizeScale * screenSize.getHeight()));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("UDP self-recognition");
        createAddressPanel(address, port);
        createButtonPanel();
        setLocationRelativeTo(null);
    }

    private void createAddressPanel(String address, String port) {
        JPanel addressPanel = new JPanel();
        JLabel addressLabel = new JLabel("multicast address:");
        JLabel portLabel = new JLabel("port:");
        addressLabel.setFont(font);
        addressPanel.add(addressLabel);
        addressPanel.add(addressField = new JTextField(address, 16));
        addressPanel.add(portLabel);
        addressPanel.add(portField = new JTextField(port, 5));
        addressField.setFont(font);
        portField.setFont(font);
        add(addressPanel, BorderLayout.NORTH);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton exitButton = new JButton("Exit");
        JButton startButton = new JButton("Start");
        buttonPanel.add(exitButton);
        buttonPanel.add(startButton);

        exitButton.addActionListener((e) -> {
            if (null != senderThread)
                senderThread.interrupt();
            if (null != receiverThread)
                receiverThread.interrupt();
            dispose();
        });
        startButton.addActionListener((e) -> {
            try {
                sender = new Sender(addressField.getText(), portField.getText());
                receiver = new Receiver(addressField.getText(),portField.getText());
                UDPFrame.this.add(new JScrollPane(table = new ReceiverTable(receiver.getTableModel(), windowSize)),
                        BorderLayout.CENTER);
                UDPFrame.this.revalidate();
                startButton.setEnabled(false);
                addressField.setEditable(false);
                portField.setEditable(false);
                senderThread = new Thread(sender);
                receiverThread = new Thread(receiver);
                senderThread.start();
                receiverThread.start();
                tableUpdateTimer = new Timer(1000, event -> {
                    table.update();
                });
                tableUpdateTimer.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
