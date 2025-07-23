package com.android.internal.policy;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class LogDecelerateInterpolator implements Interpolator {
    private int mBase;
    private int mDrift;
    private final float mLogScale;

    public LogDecelerateInterpolator(int i, int i2) {
        this.mBase = i;
        this.mDrift = i2;
        this.mLogScale = 1.0f / computeLog(1.0f, i, i2);
    }

    private static float computeLog(float f, int i, int i2) {
        return ((float) (-Math.pow(i, -f))) + 1.0f + (i2 * f);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return computeLog(f, this.mBase, this.mDrift) * this.mLogScale;
    }
}
