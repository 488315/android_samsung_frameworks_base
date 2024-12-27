package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class BounceEaseInOut implements Interpolator {
    public BounceEaseInOut() {}

    public BounceEaseInOut(Context context, AttributeSet attrs) {}

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return inout(t);
    }

    private float out(float t) {
        if (t < 0.36363636363636365d) {
            return 7.5625f * t * t;
        }
        if (t < 0.7272727272727273d) {
            float t2 = (float) (t - 0.5454545454545454d);
            return (t2 * 7.5625f * t2) + 0.75f;
        }
        if (t < 0.9090909090909091d) {
            float t3 = (float) (t - 0.8181818181818182d);
            return (t3 * 7.5625f * t3) + 0.9375f;
        }
        float t4 = (float) (t - 0.9545454545454546d);
        return (t4 * 7.5625f * t4) + 0.984375f;
    }

    private float in(float t) {
        return 1.0f - out(1.0f - t);
    }

    private float inout(float t) {
        return t < 0.5f ? in(2.0f * t) * 0.5f : (out((2.0f * t) - 1.0f) * 0.5f) + 0.5f;
    }
}
