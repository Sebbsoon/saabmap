package com.seb.model;

import java.awt.Color;

public class MapMarker {
    private int x;
    private int y;
    private int type;

    public MapMarker(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Color getColor() {
        switch (type) {
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }
}
