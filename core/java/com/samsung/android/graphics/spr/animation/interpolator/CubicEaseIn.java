package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class CubicEaseIn implements Interpolator {
    public CubicEaseIn() {
    }

    public CubicEaseIn(Context context, AttributeSet attrs) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return m271in(t);
    }

    /* renamed from: in */
    private float m271in(float t) {
        return t * t * t;
    }
}
