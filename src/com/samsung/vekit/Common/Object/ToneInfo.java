package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.ToneType;

/* loaded from: classes6.dex */
public class ToneInfo {
    private Integer maxToneType;
    private float[] toneArray;

    public ToneInfo() {
        Integer valueOf = Integer.valueOf(ToneType.values().length);
        this.maxToneType = valueOf;
        this.toneArray = new float[valueOf.intValue()];
        for (ToneType toneType : ToneType.values()) {
            this.toneArray[toneType.ordinal()] = 0.0f;
        }
    }

    public void setTone(ToneType toneType, int i) {
        this.toneArray[toneType.ordinal()] = i;
    }

    public void setToneInfo(ToneInfo toneInfo) {
        float[] toneArray = toneInfo.getToneArray();
        for (ToneType toneType : ToneType.values()) {
            this.toneArray[toneType.ordinal()] = toneArray[toneType.ordinal()];
        }
    }

    public float getTone(ToneType toneType) {
        return this.toneArray[toneType.ordinal()];
    }

    public float[] getToneArray() {
        return this.toneArray;
    }
}
