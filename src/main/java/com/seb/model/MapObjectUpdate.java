package com.seb.model;

public record MapObjectUpdate(long id, int x, int y, int type) {
    @Override
    public String toString() {
        return "MapObject{id=" + id + ", type=" + type + ", x=" + x + ", y=" + y + "}";
    }
}
