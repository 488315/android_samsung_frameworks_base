package com.android.systemui.util.view;

import android.graphics.Rect;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ViewUtil {
    public static final int $stable = 0;

    public final void setRectToViewWindowLocation(View view, Rect rect) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        rect.set(i, i2, view.getWidth() + i, view.getHeight() + i2);
    }

    public final boolean touchIsWithinView(View view, float f, float f2) {
        int i = view.getLocationOnScreen()[0];
        int i2 = view.getLocationOnScreen()[1];
        return ((float) i) <= f && f <= ((float) (view.getWidth() + i)) && ((float) i2) <= f2 && f2 <= ((float) (view.getHeight() + i2));
    }
}
