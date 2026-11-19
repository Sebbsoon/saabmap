package com.seb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.SwingUtilities;

import com.seb.client.ObjectStreamClient;
import com.seb.ui.MapFrame;

public class Main {
    private static final String CONFIG_RESOURCE = "/client.properties";

    public static void main(String[] args) {
        Properties config = loadConfig();

        String serverHost = config.getProperty("server.host", "localhost");
        int serverPort = Integer.parseInt(
                config.getProperty("server.port", "5463"));

        SwingUtilities.invokeLater(() -> {
            MapFrame mapFrame = new MapFrame();
            ObjectStreamClient client = new ObjectStreamClient(
                    mapFrame::updateObject,
                    serverHost,
                    serverPort);

            Thread clientThread = new Thread(client, "object-stream-client");
            clientThread.setDaemon(true);
            clientThread.start();
        });
    }

    private static Properties loadConfig() {
        Properties config = new Properties();

        try (InputStream input = Main.class.getResourceAsStream(CONFIG_RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Missing configuration resource: " + CONFIG_RESOURCE);
            }

            config.load(input);
            return config;
        } catch (IOException e) {
            throw new IllegalStateException("Could not load client configuration", e);
        }
    }
}