package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class IntensityInfo {
    int base;
    int max;
    int min;
    int step;

    public IntensityInfo(int i, int i2, int i3, int i4) {
        this.min = i;
        this.max = i2;
        this.base = i3;
        this.step = i4;
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

    public void setMin(int i) {
        this.min = i;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int i) {
        this.max = i;
    }

    public int getBase() {
        return this.base;
    }

    public void setBase(int i) {
        this.base = i;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int i) {
        this.step = i;
    }
}
