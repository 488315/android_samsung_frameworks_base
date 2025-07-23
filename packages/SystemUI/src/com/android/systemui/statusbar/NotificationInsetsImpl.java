package com.android.systemui.statusbar;

import android.graphics.Insets;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.WindowInsets;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationInsetsImpl extends NotificationInsetsController {
    @Override // com.android.systemui.statusbar.NotificationInsetsController
    public final Pair getinsets(WindowInsets windowInsets, DisplayCutout displayCutout) {
        int i;
        int i2;
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        if (displayCutout != null) {
            i = displayCutout.getSafeInsetLeft();
            i2 = displayCutout.getSafeInsetRight();
        } else {
            i = 0;
            i2 = 0;
        }
        return new Pair(Integer.valueOf(Math.max(insetsIgnoringVisibility.left, i)), Integer.valueOf(Math.max(insetsIgnoringVisibility.right, i2)));
    }
}
