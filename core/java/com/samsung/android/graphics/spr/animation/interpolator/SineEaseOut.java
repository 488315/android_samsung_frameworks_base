package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

public class SineEaseOut implements Interpolator {
    public SineEaseOut() {}

    public SineEaseOut(Context context, AttributeSet attrs) {}

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t);
    }

    private float out(float t) {
        return (float) Math.sin(t * 1.5707963267948966d);
    }
}
