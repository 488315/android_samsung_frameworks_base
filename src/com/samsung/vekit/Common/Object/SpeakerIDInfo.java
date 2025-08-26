package com.samsung.vekit.Common.Object;

import java.util.Arrays;

/* loaded from: classes6.dex */
public class SpeakerIDInfo {
    private String ID;
    private float[] data;
    private int size;

    public SpeakerIDInfo() {
        this.size = 256;
        float[] fArr = new float[256];
        this.data = fArr;
        Arrays.fill(fArr, 0.0f);
        this.ID = "";
    }

    public SpeakerIDInfo(float[] fArr, String str) {
        int length = fArr.length;
        this.size = length;
        this.data = Arrays.copyOf(fArr, length);
        this.ID = str;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SpeakerIDInfo m9818clone() {
        return new SpeakerIDInfo(this.data, this.ID);
    }

    public void setSpeakerIDInfo(float[] fArr, String str) {
        int length = fArr.length;
        this.size = length;
        this.data = Arrays.copyOf(fArr, length);
        this.ID = str;
    }

    public float[] getData() {
        return this.data;
    }

    public void setID(String str) {
        this.ID = str;
    }

    public String getID() {
        return this.ID;
    }
}
