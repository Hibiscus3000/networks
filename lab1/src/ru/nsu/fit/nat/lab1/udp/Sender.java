package ru.nsu.fit.nat.lab1.udp;

import java.io.IOException;
import java.net.*;

public class Sender extends Participant {

    private DatagramSocket socket;
    private byte[] buf;
    private final int bufSize = 1024;
    private final InetAddress group;
    private final String multicastMessage = "UDP self-recognition";
    private final int receiverPort;
    private final long delay = 2000;

    public Sender(String addr, String senderPort, String receiverPort) throws Exception {
        this.receiverPort = Integer.parseInt(receiverPort);
        socket = new DatagramSocket(Integer.parseInt(senderPort));
        group = InetAddress.getByName(addr);
    }

    @Override
    public void run() {
        try {
            while (true) {
                sendMessage(multicastMessage);
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            System.out.println("Sender was interrupted!");
            try {
                sendMessage(endMessage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    private void sendMessage(String message) throws IOException {
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, receiverPort);
        socket.send(packet);
    }
}
