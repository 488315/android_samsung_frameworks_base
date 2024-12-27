package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

public class ExpoEaseInOut implements Interpolator {
    public ExpoEaseInOut() {}

    public ExpoEaseInOut(Context context, AttributeSet attrs) {}

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return inout(t);
    }

    private float inout(float t) {
        if (t == 0.0f) {
            return 0.0f;
        }
        if (t >= 1.0f) {
            return 1.0f;
        }
        if (t * 2.0f < 1.0f) {
            return (float) (Math.pow(2.0d, (r7 - 1.0f) * 10.0f) * 0.5d);
        }
        return (float) (((-Math.pow(2.0d, (r7 - 1.0f) * (-10.0f))) + 2.0d) * 0.5d);
    }
}
