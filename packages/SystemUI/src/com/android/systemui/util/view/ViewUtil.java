package com.android.systemui.util.view;

import android.graphics.Rect;
import android.view.View;

/* loaded from: classes3.dex */
public final class ViewUtil {
    public static final int $stable = 0;

    public final void setRectToViewWindowLocation(View view, Rect rect) {
        ViewUtilKt.viewBoundsOnScreen(view, rect);
    }

    public final boolean touchIsWithinView(View view, float f, float f2) {
        int i = view.getLocationOnScreen()[0];
        int i2 = view.getLocationOnScreen()[1];
        return ((float) i) <= f && f <= ((float) (view.getWidth() + i)) && ((float) i2) <= f2 && f2 <= ((float) (view.getHeight() + i2));
    }
}
