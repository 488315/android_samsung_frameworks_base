package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class ElasticEaseInOut implements Interpolator {
    private float amplitude;
    private float period;

    public ElasticEaseInOut() {
    }

    public ElasticEaseInOut(float f, float f2) {
        this.amplitude = f;
        this.period = f2;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return inout(f, this.amplitude, this.period);
    }

    private float inout(float f, float f2, float f3) {
        float fAsin;
        double dPow;
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f3 == 0.0f) {
            f3 = 0.45000002f;
        }
        if (f2 == 0.0f || f2 < 1.0f) {
            fAsin = f3 / 4.0f;
            f2 = 1.0f;
        } else {
            fAsin = (float) ((f3 / 6.283185307179586d) * Math.asin(1.0f / f2));
        }
        float f4 = f * 2.0f;
        if (f4 < 1.0f) {
            float f5 = f4 - 1.0f;
            dPow = f2 * Math.pow(2.0d, 10.0f * f5) * Math.sin(((f5 - fAsin) * 6.283185307179586d) / f3) * (-0.5d);
        } else {
            float f6 = f4 - 1.0f;
            dPow = (f2 * Math.pow(2.0d, (-10.0f) * f6) * Math.sin(((f6 - fAsin) * 6.283185307179586d) / f3) * 0.5d) + 1.0d;
        }
        return (float) dPow;
    }
}
