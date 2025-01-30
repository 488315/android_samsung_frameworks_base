package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class DoodlePoint {
    private float pressure;
    private float tanX;
    private float tanY;
    private long timeStamp;

    /* renamed from: x */
    private float f3095x;

    /* renamed from: y */
    private float f3096y;

    public DoodlePoint(float x, float y, float pressure, float tanX, float tanY, long timestamp) {
        this.f3095x = x;
        this.f3096y = y;
        this.pressure = pressure;
        this.tanX = tanX;
        this.tanY = tanY;
        this.timeStamp = timestamp;
    }

    public DoodlePoint(DoodlePoint doodlePoint) {
        this.f3095x = doodlePoint.getX();
        this.f3096y = doodlePoint.getY();
        this.pressure = doodlePoint.getPressure();
        this.tanX = doodlePoint.getTanX();
        this.tanY = doodlePoint.getTanY();
        this.timeStamp = doodlePoint.getTimeStamp();
    }

    public DoodlePoint setPosition(float x, float y) {
        this.f3095x = x;
        this.f3096y = y;
        return this;
    }

    public DoodlePoint setTangent(float tanX, float tanY) {
        this.tanX = tanX;
        this.tanY = tanY;
        return this;
    }

    public float getX() {
        return this.f3095x;
    }

    public DoodlePoint setX(float x) {
        this.f3095x = x;
        return this;
    }

    public float getY() {
        return this.f3096y;
    }

    public DoodlePoint setY(float y) {
        this.f3096y = y;
        return this;
    }

    public float getPressure() {
        return this.pressure;
    }

    public DoodlePoint setPressure(float pressure) {
        this.pressure = pressure;
        return this;
    }

    public float getTanX() {
        return this.tanX;
    }

    public DoodlePoint setTanX(float tanX) {
        this.tanX = tanX;
        return this;
    }

    public float getTanY() {
        return this.tanY;
    }

    public DoodlePoint setTanY(float tanY) {
        this.tanY = tanY;
        return this;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public DoodlePoint setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }
}
