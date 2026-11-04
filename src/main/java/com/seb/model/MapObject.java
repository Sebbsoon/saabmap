package com.seb.model;

public class MapObject {
    private final long id;
    private final int type;
    private int x;
    private int y;

    public MapObject(long id, int type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "MapObject{id=" + id + ", type=" + type + ", x=" + x + ", y=" + y + "}";
    }
}
