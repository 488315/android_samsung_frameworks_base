package com.google.android.material.internal;

import android.animation.TimeInterpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReversableAnimatedValueInterpolator implements TimeInterpolator {
    public final TimeInterpolator sourceInterpolator;

    public ReversableAnimatedValueInterpolator(TimeInterpolator timeInterpolator) {
        this.sourceInterpolator = timeInterpolator;
    }

    public static TimeInterpolator of(boolean z, TimeInterpolator timeInterpolator) {
        return z ? timeInterpolator : new ReversableAnimatedValueInterpolator(timeInterpolator);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return 1.0f - this.sourceInterpolator.getInterpolation(f);
    }
}
