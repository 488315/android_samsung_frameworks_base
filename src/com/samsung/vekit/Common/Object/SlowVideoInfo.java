package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class SlowVideoInfo {
    private int captureFramerate;
    private int frameRate;
    private int numOfSVCLayer;
    private long superSlowStartTime = 0;
    private long superSlowEndTime = 0;

    public SlowVideoInfo(int i, int i2, int i3) {
        this.numOfSVCLayer = i;
        this.captureFramerate = i2;
        this.frameRate = i3;
    }

    public int getNumOfSVCLayer() {
        return this.numOfSVCLayer;
    }

    public void setNumOfSVCLayer(int i) {
        this.numOfSVCLayer = i;
    }

    public int getCaptureFramerate() {
        return this.captureFramerate;
    }

    public long getSuperSlowStartTime() {
        return this.superSlowStartTime;
    }

    public long getSuperSlowEndTime() {
        return this.superSlowEndTime;
    }

    public void setCaptureFramerate(int i) {
        this.captureFramerate = i;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public void setFrameRate(int i) {
        this.frameRate = i;
    }

    public void setSuperSlowStartTime(long j) {
        this.superSlowStartTime = j;
    }

    public void setSuperSlowEndTime(long j) {
        this.superSlowEndTime = j;
    }
}
