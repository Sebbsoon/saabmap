package com.seb.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.seb.model.Dot;
import com.seb.model.MapObject;

public class MapPanel extends JPanel {
    private final ImageIcon mapImage;
    private final Map<Long, Dot> dots = new HashMap<>();
    private static final int DOT_SIZE = 5;

    private Integer selectedCategory;

    public MapPanel(ImageIcon mapImage) {
        this.mapImage = mapImage;
        setPreferredSize(new Dimension(
                mapImage.getIconWidth(),
                mapImage.getIconHeight()));
    }

    public void updateObject(MapObject object) {
        Dot dot = dots.get(object.getId());

        if (dot == null) {
            dot = new Dot(object.getX(), object.getY(), object.getType());
            dots.put(object.getId(), dot);
            repaintDot(object.getX(), object.getY());
            return;
        }

        int oldX = dot.getX();
        int oldY = dot.getY();

        dot.setX(object.getX());
        dot.setY(object.getY());
        dot.setType(object.getType());

        repaintDot(oldX, oldY);
        repaintDot(dot.getX(), dot.getY());
    }

    private void repaintDot(int x, int y) {
        int radius = DOT_SIZE / 2;
        repaint(x - radius - 2, y - radius - 2, DOT_SIZE + 4, DOT_SIZE + 4);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(mapImage.getImage(), 0, 0, this);

        for (Dot dot : dots.values()) {
            if (!isVisibleForCategory(dot)) {
                continue;
            }

            g.setColor(dot.getColor());
            g.fillOval(
                    dot.getX() - DOT_SIZE / 2,
                    dot.getY() - DOT_SIZE / 2,
                    DOT_SIZE,
                    DOT_SIZE);
        }
    }

    public boolean isVisibleForCategory(Dot dot) {
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

    Dot getDot(long id) {
        return dots.get(id);
    }
}
