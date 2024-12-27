package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

public class CircEaseOut implements Interpolator {
    public CircEaseOut() {}

    public CircEaseOut(Context context, AttributeSet attrs) {}

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t);
    }

    private float out(float t) {
        float t2 = t - 1.0f;
        return (float) Math.sqrt(1.0f - (t2 * t2));
    }
}
