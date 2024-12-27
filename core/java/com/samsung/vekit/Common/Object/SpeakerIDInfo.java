package com.samsung.vekit.Common.Object;

import java.util.Arrays;

public class SpeakerIDInfo {
    private String ID;
    private float[] data;
    private int size;

    public SpeakerIDInfo() {
        this.size = 256;
        this.data = new float[this.size];
        Arrays.fill(this.data, 0.0f);
        this.ID = "";
    }

    public SpeakerIDInfo(float[] data, String ID) {
        this.size = data.length;
        this.data = Arrays.copyOf(data, this.size);
        this.ID = ID;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SpeakerIDInfo m9385clone() {
        return new SpeakerIDInfo(this.data, this.ID);
    }

    public void setSpeakerIDInfo(float[] data, String ID) {
        this.size = data.length;
        this.data = Arrays.copyOf(data, this.size);
        this.ID = ID;
    }

    public float[] getData() {
        return this.data;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return this.ID;
    }
}
