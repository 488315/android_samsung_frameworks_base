package com.android.server.wm;

import android.graphics.Rect;

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
}
