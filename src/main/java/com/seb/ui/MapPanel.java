package com.seb.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.seb.model.MapMarker;
import com.seb.model.MapObjectUpdate;

public class MapPanel extends JPanel {
    private final ImageIcon mapImage;
    private final Map<Long, MapMarker> dots = new HashMap<>();
    private static final int DOT_SIZE = 5;

    private Integer selectedCategory;

    public MapPanel(ImageIcon mapImage) {
        this.mapImage = mapImage;
        setPreferredSize(new Dimension(
                mapImage.getIconWidth(),
                mapImage.getIconHeight()));
    }

    public void updateObject(MapObjectUpdate object) {
        MapMarker dot = dots.get(object.id());

        if (dot == null) {
            dot = new MapMarker(object.x(), object.y(), object.type());
            dots.put(object.id(), dot);
            repaintDot(object.x(), object.y());
            return;
        }
        int oldX = dot.getX();
        int oldY = dot.getY();
        dot.setX(object.x());
        dot.setY(object.y());
        dot.setType(object.type());
        repaintDot(oldX, oldY);
        repaintDot(object.x(), object.y());

    }

    private void repaintDot(int x, int y) {
        int radius = DOT_SIZE / 2;
        repaint(x - radius - 2, y - radius - 2, DOT_SIZE + 4, DOT_SIZE + 4);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(mapImage.getImage(), 0, 0, this);

        for (MapMarker marker : dots.values()) {
            if (!isVisibleForCategory(marker)) {
                continue;
            }

            g.setColor(marker.getColor());
            g.fillOval(
                    marker.getX() - DOT_SIZE / 2,
                    marker.getY() - DOT_SIZE / 2,
                    DOT_SIZE,
                    DOT_SIZE);
        }
    }

    public boolean isVisibleForCategory(MapMarker dot) {
        if (selectedCategory == null) {
            return true;
        }

        return switch (selectedCategory) {
            case 1 -> dot.getType() == 1 || dot.getType() == 2;
            case 2 -> dot.getType() == 3;
            default -> false;
        };
    }

    public void setVisibleCategory(Integer category) {
        selectedCategory = category;
        repaint();
    }

    MapMarker getDot(long id) {
        return dots.get(id);
    }
}
