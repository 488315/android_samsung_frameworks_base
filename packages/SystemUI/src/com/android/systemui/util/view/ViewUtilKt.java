package com.android.systemui.util.view;

import android.graphics.Rect;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ViewUtilKt {
    public static final void viewBoundsOnScreen(View view, Rect rect) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        rect.set(i, i2, view.getWidth() + i, view.getHeight() + i2);
    }

    public static final Rect viewBoundsOnScreen(View view) {
        Rect rect = new Rect();
        viewBoundsOnScreen(view, rect);
        return rect;
    }
}
