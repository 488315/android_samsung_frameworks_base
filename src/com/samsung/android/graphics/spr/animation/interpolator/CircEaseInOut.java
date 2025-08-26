package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class CircEaseInOut implements Interpolator {
    public CircEaseInOut() {
    }

    public CircEaseInOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return inout(f);
    }

    private float inout(float f) {
        double dSqrt;
        double d;
        float f2 = f * 2.0f;
        if (f2 < 1.0f) {
            dSqrt = Math.sqrt(1.0f - (f2 * f2)) - 1.0d;
            d = -0.5d;
        } else {
            float f3 = f2 - 2.0f;
            dSqrt = Math.sqrt(1.0f - (f3 * f3)) + 1.0d;
            d = 0.5d;
        }
        return (float) (dSqrt * d);
    }
}
