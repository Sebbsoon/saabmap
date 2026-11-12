package com.seb.ui;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class MapFrameTest {

    private MapFrame mapFrame = new MapFrame();

    @Test
    public void testUpdateObjectWithNull() {
        BufferedImage dummyImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dummyImage.createGraphics();
        g2d.dispose();

        SwingUtilities.invokeLater(() -> {
            mapFrame.updateObject(null);
        });
    }

    @Test
    public void testUpdateObjectWithValidObject() {
        BufferedImage dummyImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dummyImage.createGraphics();
        g2d.dispose();

        com.seb.model.MapObject validObject = new com.seb.model.MapObject(1, 2, 100, 200);

        SwingUtilities.invokeLater(() -> {
            mapFrame.updateObject(validObject);
        });
    }

}
