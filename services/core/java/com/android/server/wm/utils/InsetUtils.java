package com.android.server.wm.utils;

import android.graphics.Rect;

public abstract class InsetUtils {
    public static void addInsets(Rect rect, Rect rect2) {
        rect.left += rect2.left;
        rect.top += rect2.top;
        rect.right += rect2.right;
        rect.bottom += rect2.bottom;
    }
}
