package ru.nsu.fit.nat.lab1.udp;

import ru.nsu.fit.nat.lab1.udp.table.ReceiverTableModel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.*;

public class Receiver extends Participant {

    private MulticastSocket socket;
    private final int bufSize = 1024;
    private final byte[] buf = new byte[bufSize];
    private final InetAddress group;
    private final Map<Integer, InetSocketAddress> intAddrMap = new HashMap<>();
    private final Map<InetSocketAddress, Integer> addrIntMap = new HashMap<>();
    private final Map<Integer, Date> dateMap = new HashMap<>();
    private int count = 0;

    public Receiver(String addr, String port) throws Exception {
        socket = new MulticastSocket(Integer.parseInt(port));
        group = InetAddress.getByName(addr);
        socket.joinGroup(group);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Receiver was interrupted!");
                    return;
                }
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress inetAddress = packet.getAddress();
                int port = packet.getPort();
                InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,port);
                if (!addrIntMap.containsKey(inetSocketAddress)) {
                    intAddrMap.put(count, inetSocketAddress);
                    addrIntMap.put(inetSocketAddress, count);
                    dateMap.put(addrIntMap.get(inetSocketAddress), new Date());
                    ++count;
                }
                dateMap.put(addrIntMap.get(inetSocketAddress), new Date());
                String received = new String(packet.getData(), 0, packet.getLength());
                if (endMessage == received)
                    System.out.println("Receiver: end of transmission message received!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.leaveGroup(group);
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();
        }
    }

    public ReceiverTableModel getTableModel() {
        return new ReceiverTableModel(addrIntMap, intAddrMap, dateMap);
    }

}
