package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class SlowVideoInfo {
    private int captureFramerate;
    private int frameRate;
    private int numOfSVCLayer;
    private long superSlowStartTime = 0;
    private long superSlowEndTime = 0;

    public SlowVideoInfo(int numOfSVCLayer, int captureFramerate, int frameRate) {
        this.numOfSVCLayer = numOfSVCLayer;
        this.captureFramerate = captureFramerate;
        this.frameRate = frameRate;
    }

    public int getNumOfSVCLayer() {
        return this.numOfSVCLayer;
    }

    public void setNumOfSVCLayer(int numOfSVCLayer) {
        this.numOfSVCLayer = numOfSVCLayer;
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

    public void setCaptureFramerate(int captureFramerate) {
        this.captureFramerate = captureFramerate;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public void setSuperSlowStartTime(long superSlowStartTime) {
        this.superSlowStartTime = superSlowStartTime;
    }

    public void setSuperSlowEndTime(long superSlowEndTime) {
        this.superSlowEndTime = superSlowEndTime;
    }
}
