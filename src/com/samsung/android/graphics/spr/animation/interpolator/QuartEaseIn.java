package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class QuartEaseIn implements Interpolator {
    private float in(float f) {
        return f * f * f * f;
    }

    public QuartEaseIn() {
    }

    public QuartEaseIn(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return in(f);
    }
}
