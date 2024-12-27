package com.samsung.vekit.Common.Object;

public class WaveInfo {
    private float degree;
    private int height;
    private float speed;
    private int width;

    public WaveInfo() {
        this.height = 20;
        this.width = 20;
        this.speed = 20.0f;
        this.degree = 0.0f;
    }

    public WaveInfo(int width, int height, float speed, float degree) {
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.degree = degree;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getDegree() {
        return this.degree;
    }
}
