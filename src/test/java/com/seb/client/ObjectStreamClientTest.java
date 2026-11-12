package com.seb.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.seb.model.MapObject;

public class ObjectStreamClientTest {
    ObjectStreamClient client = new ObjectStreamClient(mapObject -> {
    });

    @Test
    public void testParseValidObject() {
        String line = "ID=1;X=100;Y=200;TYPE=2";
        MapObject object = client.parseObject(line);
        assertEquals(1, object.getId());
        assertEquals(2, object.getType());
        assertEquals(100, object.getX());
        assertEquals(200, object.getY());
    }

    @Test
    public void testParseInvalidObject() {
        String line = "ID=1;X=100;Y=200;Z=300;TYPE=2";
        MapObject object = client.parseObject(line);
        assertEquals(null, object);
    }

    @Test
    public void testParseEmptyObject() {
        String line = "";
        MapObject object = client.parseObject(line);
        assertEquals(null, object);
    }

    @Test
    public void testParseNullObject() {
        MapObject object = client.parseObject(null);
        assertEquals(null, object);
    }

    @Test
    public void testParsePartialObject() {
        String line = "ID=1;X=100;TYPE=2;";
        MapObject object = client.parseObject(line);
        assertEquals(null, object);
    }

}
