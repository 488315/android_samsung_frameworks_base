package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.ToneType;

/* loaded from: classes6.dex */
public class ToneInfo {
    private Integer maxToneType = Integer.valueOf(ToneType.values().length);
    private float[] toneArray = new float[this.maxToneType.intValue()];

    public ToneInfo() {
        for (ToneType type : ToneType.values()) {
            this.toneArray[type.ordinal()] = 0.0f;
        }
    }

    public void setTone(ToneType type, int intensity) {
        this.toneArray[type.ordinal()] = intensity;
    }

    public void setToneInfo(ToneInfo srcToneInfo) {
        float[] srcToneArray = srcToneInfo.getToneArray();
        for (ToneType type : ToneType.values()) {
            this.toneArray[type.ordinal()] = srcToneArray[type.ordinal()];
        }
    }

    public float getTone(ToneType type) {
        return this.toneArray[type.ordinal()];
    }

    public float[] getToneArray() {
        return this.toneArray;
    }
}
