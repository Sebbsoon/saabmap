package com.seb;

import javax.swing.SwingUtilities;

import com.seb.client.ObjectStreamClient;
import com.seb.ui.MapFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapFrame mapFrame = new MapFrame();

            ObjectStreamClient client = new ObjectStreamClient(mapObject -> {
                System.out.println("Received object: " + mapObject);
                mapFrame.updateObject(mapObject);
            });

            Thread clientThread = new Thread(client, "object-stream-client");
            clientThread.setDaemon(true);
            clientThread.start();
        });
    }
}