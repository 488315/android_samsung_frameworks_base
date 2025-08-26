package com.samsung.android.graphics.spr.animation.interpolator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

/* loaded from: classes6.dex */
public class QuintOut50 implements Interpolator {
    private static final float[][] segments = {new float[]{0.0f, 0.502f, 0.742f}, new float[]{0.742f, 1.082f, 1.0f}};

    public QuintOut50() {
    }

    public QuintOut50(Context context, AttributeSet attributeSet) {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f / 1.0f;
        float[][] fArr = segments;
        float length = fArr.length;
        int iFloor = (int) Math.floor(length * f2);
        if (iFloor >= fArr.length) {
            iFloor = fArr.length - 1;
        }
        float f3 = (f2 - (iFloor * (1.0f / length))) * length;
        float[] fArr2 = fArr[iFloor];
        float f4 = fArr2[0];
        return ((f4 + (f3 * (((1.0f - f3) * 2.0f * (fArr2[1] - f4)) + ((fArr2[2] - f4) * f3)))) * 1.0f) + 0.0f;
    }
}
