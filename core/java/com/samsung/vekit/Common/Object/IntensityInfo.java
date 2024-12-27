package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class IntensityInfo {
    int base;
    int max;
    int min;
    int step;

    public IntensityInfo(int min, int max, int base, int step) {
        this.min = min;
        this.max = max;
        this.base = base;
        this.step = step;
    }

    public IntensityInfo() {
        this.min = 0;
        this.max = 100;
        this.base = 0;
        this.step = 2;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getBase() {
        return this.base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
