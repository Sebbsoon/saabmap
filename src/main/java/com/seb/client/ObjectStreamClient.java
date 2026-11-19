package com.seb.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import com.seb.model.MapObjectUpdate;

public class ObjectStreamClient implements Runnable {
    private final Consumer<MapObjectUpdate> onUpdate;
    static private final long INITIAL_RECONNECT_DELAY_MS = 1000;
    static private final long MAX_RECONNECT_DELAY_MS = 30000;
    private final String serverHost;
    private final int serverPort;
    private final MapObjectUpdateParser parser;

    public ObjectStreamClient(
            Consumer<MapObjectUpdate> onUpdate,
            String serverHost,
            int serverPort) {
        this.onUpdate = onUpdate;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.parser = new MapObjectUpdateParser();
    }

    @Override
    public void run() {
        long reconnectDelay = INITIAL_RECONNECT_DELAY_MS;
        while (!Thread.currentThread().isInterrupted()) {
            try (
                    Socket socket = new Socket(serverHost, serverPort);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

                System.out.println("Connected to server");
                reconnectDelay = INITIAL_RECONNECT_DELAY_MS;

                String line;
                while ((line = in.readLine()) != null) {
                    try {
                        MapObjectUpdate newObject = parser.parse(line);
                        if (newObject != null) {
                            onUpdate.accept(newObject);
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Could not parse server data: " + line);
                        e.printStackTrace(System.out);
                    }
                }
                System.out.println("Server connection closed");
            } catch (Exception e) {
                System.out.println("Server connection failed: " + e.getMessage());
            }
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            System.out.println("Reconnecting in " + reconnectDelay + " ms...");
            try {
                Thread.sleep(reconnectDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            reconnectDelay = Math.min(reconnectDelay * 2, MAX_RECONNECT_DELAY_MS);
        }
    }

}