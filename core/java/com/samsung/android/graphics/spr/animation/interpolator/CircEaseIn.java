package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class CircEaseIn implements Interpolator {
    public CircEaseIn() {}

    public CircEaseIn(Context context, AttributeSet attrs) {}

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return in(t);
    }

    private float in(float t) {
        return (float) (-(Math.sqrt(1.0f - (t * t)) - 1.0d));
    }
}
