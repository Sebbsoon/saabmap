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

    public ObjectStreamClient(Consumer<MapObject> onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public void run() {
        try (
            Socket socket = new Socket("localhost", 5463);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))
        ) {
            System.out.println("Connected to server");

            String line;
            while ((line = in.readLine()) != null) {
                try {
                    onUpdate.accept(parseObject(line));
                } catch (RuntimeException e) {
                    System.out.println("Could not parse server data: " + line);
                    e.printStackTrace(System.out);
                }
            }
        } catch (Exception e) {
            System.out.println("Server connection failed:");
            e.printStackTrace(System.out);
        }
    }

    private MapObject parseObject(String line) {
        Map<String, String> fields = new HashMap<>();

        for (String part : line.split(";")) {
            String[] pair = part.split("=", 2);
            if (pair.length != 2) {
                throw new IllegalArgumentException("Invalid field: " + part);
            }
            fields.put(pair[0].trim(), pair[1].trim());
        }

        long id = Long.parseLong(fields.get("ID"));
        int x = Integer.parseInt(fields.get("X"));
        int y = Integer.parseInt(fields.get("Y"));
        int type = Integer.parseInt(fields.get("TYPE"));

        return new MapObject(id, type, x, y);
    }
}