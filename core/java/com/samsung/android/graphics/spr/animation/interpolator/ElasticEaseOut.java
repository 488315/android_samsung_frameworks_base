package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class ElasticEaseOut implements Interpolator {
    private float amplitude;
    private float period;

    public ElasticEaseOut() {
    }

    public ElasticEaseOut(float amplitude, float period) {
        this.amplitude = amplitude;
        this.period = period;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t, this.amplitude, this.period);
    }

    private float out(float t, float a, float p) {
        float s;
        if (t == 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        if (p == 0.0f) {
            p = 0.3f;
        }
        if (a != 0.0f && a >= 1.0f) {
            s = (float) ((p / 6.283185307179586d) * Math.asin(1.0f / a));
        } else {
            a = 1.0f;
            s = p / 4.0f;
        }
        return (float) ((a * Math.pow(2.0d, (-10.0f) * t) * Math.sin(((t - s) * 6.283185307179586d) / p)) + 1.0d);
    }
}
