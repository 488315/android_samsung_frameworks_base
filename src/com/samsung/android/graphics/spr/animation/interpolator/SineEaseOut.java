package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class SineEaseOut implements Interpolator {
    public SineEaseOut() {
    }

    public SineEaseOut(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return out(f);
    }

    private float out(float f) {
        return (float) Math.sin(f * 1.5707963267948966d);
    }
}
