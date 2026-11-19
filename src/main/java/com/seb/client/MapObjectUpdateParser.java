package com.seb.client;

import java.util.HashMap;
import java.util.Map;

import com.seb.model.MapObjectUpdate;

public class MapObjectUpdateParser {
    public MapObjectUpdate parse(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        Map<String, String> fields = new HashMap<>();

        for (String part : line.split(";", -1)) {
            String[] pair = part.split("=", 2);

            if (pair.length != 2
                    || pair[0].isBlank()
                    || pair[1].isBlank()) {
                return null;
            }

            String name = pair[0].trim().toUpperCase();
            String value = pair[1].trim();

            if (!name.equals("ID")
                    && !name.equals("X")
                    && !name.equals("Y")
                    && !name.equals("TYPE")) {
                return null;
            }

            if (fields.put(name, value) != null) {
                return null;
            }
        }

        try {
            long id = Long.parseLong(fields.get("ID"));
            int x = Integer.parseInt(fields.get("X"));
            int y = Integer.parseInt(fields.get("Y"));
            int type = Integer.parseInt(fields.get("TYPE"));

            return new MapObjectUpdate(id, x, y, type);
        } catch (RuntimeException exception) {
            return null;
        }
    }
}
