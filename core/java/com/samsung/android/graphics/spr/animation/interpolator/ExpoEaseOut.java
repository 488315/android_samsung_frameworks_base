package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class ExpoEaseOut implements Interpolator {
    public ExpoEaseOut() {
    }

    public ExpoEaseOut(Context context, AttributeSet attrs) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t);
    }

    private float out(float t) {
        return (float) (t < 1.0f ? 1.0d + (-Math.pow(2.0d, (-10.0f) * t)) : 1.0d);
    }
}
