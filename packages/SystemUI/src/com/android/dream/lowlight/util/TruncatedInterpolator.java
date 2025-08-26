package com.android.dream.lowlight.util;

import android.view.animation.Interpolator;

/* loaded from: classes.dex */
public final class TruncatedInterpolator implements Interpolator {
    public final Interpolator baseInterpolator;
    public final float scaleFactor;

    public TruncatedInterpolator(Interpolator interpolator, float f, float f2) {
        this.baseInterpolator = interpolator;
        this.scaleFactor = f2 / f;
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return this.baseInterpolator.getInterpolation(f * this.scaleFactor);
    }
}
