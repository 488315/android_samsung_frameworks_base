package com.android.systemui.statusbar;

import android.graphics.Insets;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.WindowInsets;

/* loaded from: classes3.dex */
public class NotificationInsetsImpl extends NotificationInsetsController {
    @Override // com.android.systemui.statusbar.NotificationInsetsController
    public final Pair getinsets(WindowInsets windowInsets, DisplayCutout displayCutout) {
        int safeInsetLeft;
        int safeInsetRight;
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        if (displayCutout != null) {
            safeInsetLeft = displayCutout.getSafeInsetLeft();
            safeInsetRight = displayCutout.getSafeInsetRight();
        } else {
            safeInsetLeft = 0;
            safeInsetRight = 0;
        }
        return new Pair(Integer.valueOf(Math.max(insetsIgnoringVisibility.left, safeInsetLeft)), Integer.valueOf(Math.max(insetsIgnoringVisibility.right, safeInsetRight)));
    }
}
