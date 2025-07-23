package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class QuadEaseOut implements Interpolator {
    private float out(float f) {
        return (-f) * (f - 2.0f);
    }

    public QuadEaseOut() {
    }

    public QuadEaseOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f);
    }
}
