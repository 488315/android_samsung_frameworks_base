package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class SineEaseInOut implements Interpolator {
    public SineEaseInOut() {
    }

    public SineEaseInOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return inout(f);
    }

    private float inout(float f) {
        return (float) ((Math.cos(f * 3.141592653589793d) - 1.0d) * (-0.5d));
    }
}
