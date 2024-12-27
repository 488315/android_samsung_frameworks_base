package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class BackEaseOut implements Interpolator {
    private float overshot;

    public BackEaseOut() {}

    public BackEaseOut(float overshot) {
        this.overshot = overshot;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t, this.overshot);
    }

    private float out(float t, float o) {
        if (o == 0.0f) {
            o = 1.70158f;
        }
        float t2 = t - 1.0f;
        return (t2 * t2 * (((o + 1.0f) * t2) + o)) + 1.0f;
    }
}
