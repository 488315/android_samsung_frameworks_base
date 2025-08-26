package com.android.wm.shell.back;

import android.graphics.RectF;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public abstract class CrossActivityBackAnimationKt {
    public static void scaleCentered$default(RectF rectF, float f) {
        float fWidth = (rectF.width() / 2) + rectF.left;
        float fHeight = (rectF.height() / 2) + rectF.top;
        rectF.offset(-fWidth, -fHeight);
        rectF.scale(f);
        rectF.offset(fWidth, fHeight);
    }

    public static final void setInterpolatedRectF(RectF rectF, RectF rectF2, RectF rectF3, float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Progress value must be between 0 and 1");
        }
        float f2 = rectF2.left;
        rectF.left = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rectF3.left, f2, f, f2);
        float f3 = rectF2.top;
        rectF.top = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rectF3.top, f3, f, f3);
        float f4 = rectF2.right;
        rectF.right = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rectF3.right, f4, f, f4);
        float f5 = rectF2.bottom;
        rectF.bottom = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(rectF3.bottom, f5, f, f5);
    }
}
