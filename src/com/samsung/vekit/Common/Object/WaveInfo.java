package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
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

    public WaveInfo(int i, int i2, float f, float f2) {
        this.height = i2;
        this.width = i;
        this.speed = f;
        this.degree = f2;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setSpeed(float f) {
        this.speed = f;
    }

    public void setDegree(float f) {
        this.degree = f;
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
