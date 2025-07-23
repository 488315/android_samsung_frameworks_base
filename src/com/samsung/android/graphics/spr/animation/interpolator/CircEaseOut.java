package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class CircEaseOut implements Interpolator {
    public CircEaseOut() {
    }

    public CircEaseOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f);
    }

    private float out(float f) {
        float f2 = f - 1.0f;
        return (float) Math.sqrt(1.0f - (f2 * f2));
    }
}
