package com.seb.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.seb.model.MapObject;

public class ObjectStreamClient implements Runnable {
    private final Consumer<MapObject> onUpdate;
    private final long INITIAL_RECONNECT_DELAY_MS = 1000;
    private final long MAX_RECONNECT_DELAY_MS = 30000;

    public ObjectStreamClient(Consumer<MapObject> onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public void run() {
        long reconnectDelay = INITIAL_RECONNECT_DELAY_MS;
        while (!Thread.currentThread().isInterrupted()) {
            try (
                    Socket socket = new Socket("localhost", 5463);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

                System.out.println("Connected to server");

                String line;
                while ((line = in.readLine()) != null) {
                    try {
                        MapObject newObject = parseObject(line);
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

    public MapObject parseObject(String line) {
        if (line == null || line.isBlank()) {
            System.out.println("Input line is empty");
            return null;
        }

        Map<String, String> fields = new HashMap<>();

        for (String part : line.split(";", -1)) {
            String[] pair = part.split("=", 2);

            if (pair.length != 2
                    || pair[0].isBlank()
                    || pair[1].isBlank()) {
                System.out.println("Invalid field: " + part);
                return null;
            }

            String name = pair[0].trim().toUpperCase();
            String value = pair[1].trim();

            if (!name.equals("ID")
                    && !name.equals("X")
                    && !name.equals("Y")
                    && !name.equals("TYPE")) {
                System.out.println("Unknown field: " + name);
                return null;
            }

            if (fields.put(name, value) != null) {
                System.out.println("Duplicate field: " + name);
                return null;
            }
        }

        long id = Long.parseLong(fields.get("ID"));
        int x = Integer.parseInt(fields.get("X"));
        int y = Integer.parseInt(fields.get("Y"));
        int type = Integer.parseInt(fields.get("TYPE"));

        return new MapObject(id, type, x, y);
    }

}