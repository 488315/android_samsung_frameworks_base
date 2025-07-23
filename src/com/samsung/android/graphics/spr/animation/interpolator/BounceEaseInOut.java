package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class BounceEaseInOut implements Interpolator {
    private float out(float f) {
        double d = f;
        if (d < 0.36363636363636365d) {
            return 7.5625f * f * f;
        }
        if (d < 0.7272727272727273d) {
            float f2 = (float) (d - 0.5454545454545454d);
            return (7.5625f * f2 * f2) + 0.75f;
        }
        if (d < 0.9090909090909091d) {
            float f3 = (float) (d - 0.8181818181818182d);
            return (7.5625f * f3 * f3) + 0.9375f;
        }
        float f4 = (float) (d - 0.9545454545454546d);
        return (7.5625f * f4 * f4) + 0.984375f;
    }

    public BounceEaseInOut() {
    }

    public BounceEaseInOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return inout(f);
    }

    private float in(float f) {
        return 1.0f - out(1.0f - f);
    }

    private float inout(float f) {
        if (f < 0.5f) {
            return in(f * 2.0f) * 0.5f;
        }
        return (out((f * 2.0f) - 1.0f) * 0.5f) + 0.5f;
    }
}
