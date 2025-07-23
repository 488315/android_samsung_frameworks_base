package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class Region {
    private long endTime;
    private boolean isFrcOn;
    private float speed;
    private long startTime;

    public Region getRegion() {
        return this;
    }

    public Region(long j, long j2, float f, boolean z) {
        this.startTime = j;
        this.endTime = j2;
        this.speed = f;
        this.isFrcOn = z;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public float getSpeed() {
        return this.speed;
    }

    public boolean isFrcOn() {
        return this.isFrcOn;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }

    public void setEndTime(long j) {
        this.endTime = j;
    }

    public void setSpeed(float f) {
        this.speed = f;
    }

    public void setFrcOn(boolean z) {
        this.isFrcOn = z;
    }

    @Deprecated
    public void setStartTime(int i) {
        this.startTime = i;
    }

    @Deprecated
    public void setEndTime(int i) {
        this.endTime = i;
    }
}
