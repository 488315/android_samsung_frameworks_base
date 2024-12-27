package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.PcmSampleType;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class PcmInfo {
    private float[] data;
    private long endTimeMs;
    private String key;
    private PcmSampleType sampleType;
    private int size;
    private long startTimeMs;

    PcmInfo() {
        this.key = "";
        this.startTimeMs = 0L;
        this.endTimeMs = 0L;
        this.sampleType = PcmSampleType.FIRST;
        this.size = 0;
        this.data = null;
    }

    PcmInfo(String key, long startTimeMs, long endTimeMs, PcmSampleType sampleType, int size, float[] data) {
        this.key = key;
        this.startTimeMs = startTimeMs;
        this.endTimeMs = endTimeMs;
        this.sampleType = sampleType;
        this.size = size;
        this.data = Arrays.copyOf(data, size);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getStartTimeMs() {
        return this.startTimeMs;
    }

    public void setStartTimeMs(long startTimeMs) {
        this.startTimeMs = startTimeMs;
    }

    public long getEndTimeMs() {
        return this.endTimeMs;
    }

    public void setEndTimeMs(long endTimeMs) {
        this.endTimeMs = endTimeMs;
    }

    public PcmSampleType getSampleType() {
        return this.sampleType;
    }

    public void setSampleType(PcmSampleType sampleType) {
        this.sampleType = sampleType;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float[] getData() {
        return this.data;
    }

    public void setData(float[] data) {
        this.data = data;
    }
}
