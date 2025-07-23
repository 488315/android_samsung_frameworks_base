package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class CubicEaseOut implements Interpolator {
    private float out(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }

    public CubicEaseOut() {
    }

    public CubicEaseOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f);
    }
}
