package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.ToneType;

/* loaded from: classes6.dex */
public class Tone {
    private int intensity;
    ToneType toneType;

    public Tone(ToneType toneType) {
        this.toneType = toneType;
        this.intensity = 0;
    }

    public Tone(ToneType toneType, int intensity) {
        this.toneType = toneType;
        this.intensity = intensity;
    }

    public ToneType getToneType() {
        return this.toneType;
    }

    public int getIntensity() {
        return this.intensity;
    }

    public Tone setIntensity(int intensity) {
        this.intensity = intensity;
        return this;
    }
}
