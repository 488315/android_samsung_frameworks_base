package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.hardware.scontext.SContextConstants;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class ExpoEaseIn implements Interpolator {
    public ExpoEaseIn() {
    }

    public ExpoEaseIn(Context context, AttributeSet attrs) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return in(t);
    }

    private float in(float t) {
        return (float) (t == 0.0f ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : Math.pow(2.0d, (t - 1.0f) * 10.0f));
    }
}
