package com.android.server.wm.utils;

import android.graphics.Rect;

/* loaded from: classes3.dex */
public abstract class InsetUtils {
    public static void rotateInsets(Rect rect, int i) {
        if (i != 0) {
            if (i == 1) {
                rect.set(rect.top, rect.right, rect.bottom, rect.left);
                return;
            }
            if (i == 2) {
                rect.set(rect.right, rect.bottom, rect.left, rect.top);
            } else {
                if (i == 3) {
                    rect.set(rect.bottom, rect.left, rect.top, rect.right);
                    return;
                }
                throw new IllegalArgumentException("Unknown rotation: " + i);
            }
        }
    }

    public static void addInsets(Rect rect, Rect rect2) {
        rect.left += rect2.left;
        rect.top += rect2.top;
        rect.right += rect2.right;
        rect.bottom += rect2.bottom;
    }
}
