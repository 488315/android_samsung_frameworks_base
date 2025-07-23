package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class FilterOption {
    private float contrast;
    private float grain;
    private float saturation;
    private float temperature;

    public FilterOption() {
        this.contrast = 0.0f;
        this.saturation = 0.0f;
        this.temperature = 0.0f;
        this.grain = 0.0f;
    }

    public FilterOption(float f, float f2, float f3, float f4) {
        this.contrast = f;
        this.saturation = f2;
        this.temperature = f3;
        this.grain = f4;
    }

    public FilterOption(FilterOption filterOption) {
        this.contrast = filterOption.contrast;
        this.saturation = filterOption.saturation;
        this.temperature = filterOption.temperature;
        this.grain = filterOption.grain;
    }

    public float getContrast() {
        return this.contrast;
    }

    public void setContrast(float f) {
        this.contrast = f;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public void setSaturation(float f) {
        this.saturation = f;
    }

    public float getTemperature() {
        return this.temperature;
    }

    public void setTemperature(float f) {
        this.temperature = f;
    }

    public float getGrain() {
        return this.grain;
    }

    public void setGrain(float f) {
        this.grain = f;
    }
}
