package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

public class QuartEaseIn implements Interpolator {
    public QuartEaseIn() {}

    public QuartEaseIn(Context context, AttributeSet attrs) {}

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return in(t);
    }

    private float in(float t) {
        return t * t * t * t;
    }
}
