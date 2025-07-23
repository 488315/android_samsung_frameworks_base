package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class SineEaseIn implements Interpolator {
    public SineEaseIn() {
    }

    public SineEaseIn(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return in(f);
    }

    private float in(float f) {
        return (float) ((-Math.cos(f * 1.5707963267948966d)) + 1.0d);
    }
}
