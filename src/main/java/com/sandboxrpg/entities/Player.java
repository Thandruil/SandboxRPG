package com.sandboxrpg.entities;

public class Player {

    private float x;
    private float y;

    public int strength;

    public Player() {
        this.x = 0;
        this.y = 0;
        this.strength = 500000000;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
