package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class DoodlePoint {
    private float pressure;
    private float tanX;
    private float tanY;
    private long timeStamp;
    private float x;
    private float y;

    public DoodlePoint(float f, float f2, float f3, float f4, float f5, long j) {
        this.x = f;
        this.y = f2;
        this.pressure = f3;
        this.tanX = f4;
        this.tanY = f5;
        this.timeStamp = j;
    }

    public DoodlePoint(DoodlePoint doodlePoint) {
        this.x = doodlePoint.getX();
        this.y = doodlePoint.getY();
        this.pressure = doodlePoint.getPressure();
        this.tanX = doodlePoint.getTanX();
        this.tanY = doodlePoint.getTanY();
        this.timeStamp = doodlePoint.getTimeStamp();
    }

    public DoodlePoint setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
        return this;
    }

    public DoodlePoint setTangent(float f, float f2) {
        this.tanX = f;
        this.tanY = f2;
        return this;
    }

    public float getX() {
        return this.x;
    }

    public DoodlePoint setX(float f) {
        this.x = f;
        return this;
    }

    public float getY() {
        return this.y;
    }

    public DoodlePoint setY(float f) {
        this.y = f;
        return this;
    }

    public float getPressure() {
        return this.pressure;
    }

    public DoodlePoint setPressure(float f) {
        this.pressure = f;
        return this;
    }

    public float getTanX() {
        return this.tanX;
    }

    public DoodlePoint setTanX(float f) {
        this.tanX = f;
        return this;
    }

    public float getTanY() {
        return this.tanY;
    }

    public DoodlePoint setTanY(float f) {
        this.tanY = f;
        return this;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public DoodlePoint setTimeStamp(long j) {
        this.timeStamp = j;
        return this;
    }
}
