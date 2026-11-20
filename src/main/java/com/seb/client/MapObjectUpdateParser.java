package com.seb.client;

import java.util.HashMap;
import java.util.Map;

import com.seb.model.MapObjectUpdate;

public class MapObjectUpdateParser {
    public MapObjectUpdate parse(String line) {
        if (line == null || line.isBlank()) {
            return invalid(line, "empty line");
        }

        Map<String, String> fields = new HashMap<>();

        for (String part : line.split(";", -1)) {
            String[] pair = part.split("=", 2);

            if (pair.length != 2
                    || pair[0].isBlank()
                    || pair[1].isBlank()) {
                return invalid(line, "invalid field format");
            }

            String name = pair[0].trim().toUpperCase();
            String value = pair[1].trim();

            if (!name.equals("ID")
                    && !name.equals("X")
                    && !name.equals("Y")
                    && !name.equals("TYPE")) {
                return invalid(line, "invalid field name");
            }

            if (fields.put(name, value) != null) {
                return invalid(line, "duplicate field");
            }
        }

        try {
            String idValue = fields.get("ID");
            String xValue = fields.get("X");
            String yValue = fields.get("Y");
            String typeValue = fields.get("TYPE");

            if (idValue == null || xValue == null
                    || yValue == null || typeValue == null) {
                return invalid(line, "missing required field");
            }

            long id = Long.parseLong(idValue);
            int x = Integer.parseInt(xValue);
            int y = Integer.parseInt(yValue);
            int type = Integer.parseInt(typeValue);

            if (id < 0 || type < 1 || type > 3) {
                return invalid(line, "invalid ID or type");
            }

            return new MapObjectUpdate(id, x, y, type);
        } catch (RuntimeException exception) {
            return invalid(line, "parsing error");
        }
    }

    private MapObjectUpdate invalid(String line, String reason) {
        System.out.println("Invalid server data: " + line
                + " (" + reason + ")");
        return null;
    }
}
