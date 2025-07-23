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

    PcmInfo(String str, long j, long j2, PcmSampleType pcmSampleType, int i, float[] fArr) {
        this.key = str;
        this.startTimeMs = j;
        this.endTimeMs = j2;
        this.sampleType = pcmSampleType;
        this.size = i;
        this.data = Arrays.copyOf(fArr, i);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public long getStartTimeMs() {
        return this.startTimeMs;
    }

    public void setStartTimeMs(long j) {
        this.startTimeMs = j;
    }

    public long getEndTimeMs() {
        return this.endTimeMs;
    }

    public void setEndTimeMs(long j) {
        this.endTimeMs = j;
    }

    public PcmSampleType getSampleType() {
        return this.sampleType;
    }

    public void setSampleType(PcmSampleType pcmSampleType) {
        this.sampleType = pcmSampleType;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public float[] getData() {
        return this.data;
    }

    public void setData(float[] fArr) {
        this.data = fArr;
    }
}
