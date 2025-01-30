package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;

/* loaded from: classes3.dex */
public abstract class LaunchParamsUtil {
    public static final Rect TMP_STABLE_BOUNDS = new Rect();

    public static void centerBounds(TaskDisplayArea taskDisplayArea, int i, int i2, Rect rect) {
        if (rect.isEmpty()) {
            taskDisplayArea.getStableRect(rect);
        }
        int centerX = rect.centerX() - (i / 2);
        int centerY = rect.centerY() - (i2 / 2);
        rect.set(centerX, centerY, i + centerX, i2 + centerY);
    }

    public static void adjustBoundsToFitInDisplayArea(TaskDisplayArea taskDisplayArea, int i, ActivityInfo.WindowLayout windowLayout, Rect rect) {
        int i2;
        Rect rect2 = TMP_STABLE_BOUNDS;
        if (taskDisplayArea.isDesktopModeEnabled()) {
            taskDisplayArea.getDisplayContent().getStableRect(rect2, false);
            rect2.intersect(taskDisplayArea.getBounds());
        } else {
            taskDisplayArea.getStableRect(rect2);
            int i3 = (int) (((taskDisplayArea.getConfiguration().densityDpi / 160.0f) * 27.0f) + 0.5f);
            rect2.inset(i3, i3);
        }
        if (rect2.width() < rect.width() || rect2.height() < rect.height()) {
            float min = Math.min(rect2.width() / rect.width(), rect2.height() / rect.height());
            int i4 = windowLayout == null ? -1 : windowLayout.minWidth;
            int i5 = windowLayout != null ? windowLayout.minHeight : -1;
            int max = Math.max(i4, (int) (rect.width() * min));
            int max2 = Math.max(i5, (int) (rect.height() * min));
            if (rect2.width() < max || rect2.height() < max2) {
                if (i == 1) {
                    i2 = rect2.right - max;
                } else {
                    i2 = rect2.left;
                }
                int i6 = rect2.top;
                rect.set(i2, i6, max + i2, max2 + i6);
                return;
            }
            int i7 = rect.left;
            int i8 = rect.top;
            rect.set(i7, i8, max + i7, max2 + i8);
        }
        int i9 = rect.right;
        int i10 = rect2.right;
        int i11 = (i9 <= i10 && (i9 = rect.left) >= (i10 = rect2.left)) ? 0 : i10 - i9;
        int i12 = rect.top;
        int i13 = rect2.top;
        rect.offset(i11, (i12 < i13 || (i12 = rect.bottom) > (i13 = rect2.bottom)) ? i13 - i12 : 0);
    }
}
