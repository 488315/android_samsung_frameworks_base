package com.samsung.android.graphics.spr.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class BackEaseIn implements Interpolator {
    private float overshot;

    public BackEaseIn() {
    }

    public BackEaseIn(float overshot) {
        this.overshot = overshot;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return in(t, this.overshot);
    }

    private float in(float t, float o) {
        if (o == 0.0f) {
            o = 1.70158f;
        }
        return t * t * (((1.0f + o) * t) - o);
    }
}
