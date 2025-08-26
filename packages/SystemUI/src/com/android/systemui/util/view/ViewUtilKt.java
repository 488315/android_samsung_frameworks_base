package com.android.systemui.util.view;

import android.graphics.Rect;
import android.view.View;

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
