package com.android.wm.shell.common.split;

import android.graphics.Rect;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DockedDividerUtils {
    public static void calculateBoundsForCellWithPosition(Rect rect, Rect rect2, int i, int i2, int i3) {
        rect.set(rect2);
        boolean z = true;
        if (i2 == 1) {
            rect.right = i;
        } else if (i2 == 2) {
            rect.bottom = i;
        } else if (i2 == 3) {
            rect.left = i + i3;
        } else if (i2 == 4) {
            rect.top = i + i3;
        }
        if (i2 != 1 && i2 != 2) {
            z = false;
        }
        sanitizeStackBounds(rect, z);
        rect.intersect(rect2);
    }

    public static void calculateBoundsForPosition(int i, int i2, Rect rect, int i3, int i4, int i5, Rect rect2) {
        rect.set(0, 0, i3, i4);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && rect2 != null) {
            rect.inset(rect2);
        }
        if (i2 == 1) {
            rect.right = i;
        } else if (i2 == 2) {
            rect.bottom = i;
        } else if (i2 == 3) {
            rect.left = i + i5;
        } else if (i2 == 4) {
            rect.top = i + i5;
        }
        sanitizeStackBounds(rect, i2 == 1 || i2 == 2);
    }

    public static float calculateSplitRatio(Rect rect, Rect rect2, Rect rect3, int i) {
        int i2;
        int i3;
        int dockSide = getDockSide(rect, rect2);
        Rect rect4 = new Rect(rect2);
        if (rect3 != null) {
            rect4.inset(rect3);
        }
        if (dockSide == 1) {
            i2 = rect.right;
        } else if (dockSide != 2) {
            if (dockSide == 3) {
                i3 = rect.left;
            } else if (dockSide != 4) {
                i2 = 0;
            } else {
                i3 = rect.top;
            }
            i2 = i3 - i;
        } else {
            i2 = rect.bottom;
        }
        return (i2 - ((dockSide == 2 || dockSide == 4) ? rect4.top : rect4.left)) / (((dockSide == 2 || dockSide == 4) ? rect4.height() : rect2.width()) - i);
    }

    public static int getDockSide(Rect rect, Rect rect2) {
        int i = rect.bottom;
        int i2 = rect2.bottom;
        if (i == i2 && rect.left == rect2.left && rect.right < rect2.right) {
            return 1;
        }
        int i3 = rect.top;
        int i4 = rect2.top;
        if (i3 == i4 && rect.left == rect2.left && i < i2) {
            return 2;
        }
        if (i3 == i4 && rect.right == rect2.right && rect2.left < rect.left) {
            return 3;
        }
        return (i == i2 && rect.right == rect2.right && i4 < i3) ? 4 : -1;
    }

    public static void sanitizeStackBounds(Rect rect, boolean z) {
        if (z) {
            int i = rect.left;
            int i2 = rect.right;
            if (i >= i2) {
                rect.left = i2 - 1;
            }
            int i3 = rect.top;
            int i4 = rect.bottom;
            if (i3 >= i4) {
                rect.top = i4 - 1;
                return;
            }
            return;
        }
        int i5 = rect.right;
        int i6 = rect.left;
        if (i5 <= i6) {
            rect.right = i6 + 1;
        }
        int i7 = rect.bottom;
        int i8 = rect.top;
        if (i7 <= i8) {
            rect.bottom = i8 + 1;
        }
    }
}
