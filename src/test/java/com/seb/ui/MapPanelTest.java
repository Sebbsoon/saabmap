package com.seb.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import com.seb.model.Dot;
import com.seb.model.MapObject;

class MapPanelTest {

    private final MapPanel panel = new MapPanel(new ImageIcon(
            new byte[0]));

    @Test
    void categoryOneShowsTypesOneAndTwo() {
        panel.setVisibleCategory(1);

        assertTrue(panel.isVisibleForCategory(new Dot(0, 0, 1)));
        assertTrue(panel.isVisibleForCategory(new Dot(0, 0, 2)));
        assertFalse(panel.isVisibleForCategory(new Dot(0, 0, 3)));
    }

    @Test
    void categoryTwoShowsTypeThreeOnly() {
        panel.setVisibleCategory(2);

        assertFalse(panel.isVisibleForCategory(new Dot(0, 0, 1)));
        assertFalse(panel.isVisibleForCategory(new Dot(0, 0, 2)));
        assertTrue(panel.isVisibleForCategory(new Dot(0, 0, 3)));
    }

    @Test
    void nullCategoryShowsAllTypes() {
        panel.setVisibleCategory(null);

        assertTrue(panel.isVisibleForCategory(new Dot(0, 0, 1)));
        assertTrue(panel.isVisibleForCategory(new Dot(0, 0, 2)));
        assertTrue(panel.isVisibleForCategory(new Dot(0, 0, 3)));
    }

    @Test
    void updateObjectCreatesAndUpdatesDot() {
        panel.updateObject(new MapObject(10, 20, 30, 1));

        Dot first = panel.getDot(10);
        assertEquals(20, first.getX());
        assertEquals(30, first.getY());
        assertEquals(1, first.getType());

        panel.updateObject(new MapObject(10, 40, 50, 3));

        Dot updated = panel.getDot(10);
        assertEquals(40, updated.getX());
        assertEquals(50, updated.getY());
        assertEquals(3, updated.getType());
    }
}
