package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class BackEaseOut implements Interpolator {
    private float overshot;

    private float out(float f, float f2) {
        if (f2 == 0.0f) {
            f2 = 1.70158f;
        }
        float f3 = f - 1.0f;
        return (f3 * f3 * (((f2 + 1.0f) * f3) + f2)) + 1.0f;
    }

    public BackEaseOut() {
    }

    public BackEaseOut(float f) {
        this.overshot = f;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f, this.overshot);
    }
}
