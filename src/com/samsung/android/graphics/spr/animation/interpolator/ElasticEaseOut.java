package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class ElasticEaseOut implements Interpolator {
    private float amplitude;
    private float period;

    public ElasticEaseOut() {
    }

    public ElasticEaseOut(float f, float f2) {
        this.amplitude = f;
        this.period = f2;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f, this.amplitude, this.period);
    }

    private float out(float f, float f2, float f3) {
        float f4;
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f3 == 0.0f) {
            f3 = 0.3f;
        }
        if (f2 == 0.0f || f2 < 1.0f) {
            f4 = f3 / 4.0f;
            f2 = 1.0f;
        } else {
            f4 = (float) ((f3 / 6.283185307179586d) * Math.asin(1.0f / f2));
        }
        return (float) ((f2 * Math.pow(2.0d, (-10.0f) * f) * Math.sin(((f - f4) * 6.283185307179586d) / f3)) + 1.0d);
    }
}
