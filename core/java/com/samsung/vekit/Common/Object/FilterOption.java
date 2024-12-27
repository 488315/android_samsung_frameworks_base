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

    public FilterOption(float contrast, float saturation, float temperature, float grain) {
        this.contrast = contrast;
        this.saturation = saturation;
        this.temperature = temperature;
        this.grain = grain;
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

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getTemperature() {
        return this.temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getGrain() {
        return this.grain;
    }

    public void setGrain(float grain) {
        this.grain = grain;
    }
}
