package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class CircEaseInOut implements Interpolator {
    public CircEaseInOut() {}

    public CircEaseInOut(Context context, AttributeSet attrs) {}

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return inout(t);
    }

    private float inout(float t) {
        float t2 = t * 2.0f;
        if (t2 < 1.0f) {
            return (float) ((Math.sqrt(1.0f - (t2 * t2)) - 1.0d) * (-0.5d));
        }
        float t3 = t2 - 2.0f;
        return (float) ((Math.sqrt(1.0f - (t3 * t3)) + 1.0d) * 0.5d);
    }
}
