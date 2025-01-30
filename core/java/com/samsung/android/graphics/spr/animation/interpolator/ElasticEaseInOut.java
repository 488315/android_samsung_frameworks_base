package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class ElasticEaseInOut implements Interpolator {
    private float amplitude;
    private float period;

    public ElasticEaseInOut() {
    }

    public ElasticEaseInOut(float amplitude, float period) {
        this.amplitude = amplitude;
        this.period = period;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return inout(t, this.amplitude, this.period);
    }

    private float inout(float t, float a, float p) {
        float s;
        if (t == 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        if (p == 0.0f) {
            p = 0.45000002f;
        }
        if (a != 0.0f && a >= 1.0f) {
            s = (float) ((p / 6.283185307179586d) * Math.asin(1.0f / a));
        } else {
            a = 1.0f;
            s = p / 4.0f;
        }
        float t2 = t * 2.0f;
        if (t2 < 1.0f) {
            float t3 = t2 - 1.0f;
            return (float) (a * Math.pow(2.0d, t3 * 10.0f) * Math.sin(((t3 - s) * 6.283185307179586d) / p) * (-0.5d));
        }
        float t4 = t2 - 1.0f;
        return (float) ((a * Math.pow(2.0d, t4 * (-10.0f)) * Math.sin(((t4 - s) * 6.283185307179586d) / p) * 0.5d) + 1.0d);
    }
}
