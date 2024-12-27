package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class BackEaseInOut implements Interpolator {
    private float overshot;

    public BackEaseInOut() {}

    public BackEaseInOut(float overshot) {
        this.overshot = overshot;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return inout(t, this.overshot);
    }

    private float inout(float t, float o) {
        if (o == 0.0f) {
            o = 1.70158f;
        }
        float t2 = t * 2.0f;
        if (t2 < 1.0f) {
            float o2 = (float) (o * 1.525d);
            return t2 * t2 * (((o2 + 1.0f) * t2) - o2) * 0.5f;
        }
        float t3 = t2 - 2.0f;
        float o3 = (float) (o * 1.525d);
        return ((t3 * t3 * (((o3 + 1.0f) * t3) + o3)) + 2.0f) * 0.5f;
    }
}
