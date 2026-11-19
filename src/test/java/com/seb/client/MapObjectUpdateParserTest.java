package com.seb.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.seb.model.MapObjectUpdate;

public class MapObjectUpdateParserTest {
    private final MapObjectUpdateParser parser = new MapObjectUpdateParser();

    @Test
    public void testParseValidObject() {
        MapObjectUpdate object = parser.parse("ID=1;X=100;Y=200;TYPE=2");

        assertEquals(1, object.id());
        assertEquals(100, object.x());
        assertEquals(200, object.y());
        assertEquals(2, object.type());
    }

    @Test
    public void testParseInvalidObject() {
        assertNull(parser.parse("ID=1;X=100;Y=200;Z=300;TYPE=2"));
    }

    @Test
    public void testParseEmptyObject() {
        assertNull(parser.parse(""));
    }

    @Test
    public void testParseNullObject() {
        assertNull(parser.parse(null));
    }

    @Test
    public void testParsePartialObject() {
        assertNull(parser.parse("ID=1;X=100;TYPE=2;"));
    }
}
