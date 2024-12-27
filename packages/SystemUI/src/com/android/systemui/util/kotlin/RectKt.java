package com.android.systemui.util.kotlin;

import android.graphics.Rect;

public final class RectKt {
    public static final long getArea(Rect rect) {
        return rect.width() * rect.height();
    }
}
