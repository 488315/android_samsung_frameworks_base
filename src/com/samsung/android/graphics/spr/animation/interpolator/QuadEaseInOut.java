package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class QuadEaseInOut implements Interpolator {
    private float inout(float f) {
        float f2 = f * 2.0f;
        if (f2 < 1.0f) {
            return 0.5f * f2 * f2;
        }
        float f3 = f2 - 1.0f;
        return ((f3 * (f3 - 2.0f)) - 1.0f) * (-0.5f);
    }

    public QuadEaseInOut() {
    }

    public QuadEaseInOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return inout(f);
    }
}
