package ru.nsu.fit.nat.lab1;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        try {
            if (3 != args.length) {
                throw new InvalidArgumentsNumberException(3, args.length);
            }
            EventQueue.invokeLater(() -> {
                UDPFrame UDPframe = null;
                try {
                    UDPframe = new UDPFrame(args[0],args[1],args[2]);
                    UDPframe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
