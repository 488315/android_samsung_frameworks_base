package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class ExpoEaseOut implements Interpolator {
    public ExpoEaseOut() {
    }

    public ExpoEaseOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f);
    }

    private float out(float f) {
        return (float) (f < 1.0f ? 1.0d + (-Math.pow(2.0d, f * (-10.0f))) : 1.0d);
    }
}
