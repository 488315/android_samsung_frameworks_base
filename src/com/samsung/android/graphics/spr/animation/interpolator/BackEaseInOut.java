package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class BackEaseInOut implements Interpolator {
    private float overshot;

    private float inout(float f, float f2) {
        if (f2 == 0.0f) {
            f2 = 1.70158f;
        }
        float f3 = f * 2.0f;
        if (f3 < 1.0f) {
            float f4 = (float) (f2 * 1.525d);
            return f3 * f3 * (((1.0f + f4) * f3) - f4) * 0.5f;
        }
        float f5 = f3 - 2.0f;
        float f6 = (float) (f2 * 1.525d);
        return ((f5 * f5 * (((1.0f + f6) * f5) + f6)) + 2.0f) * 0.5f;
    }

    public BackEaseInOut() {
    }

    public BackEaseInOut(float f) {
        this.overshot = f;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return inout(f, this.overshot);
    }
}
