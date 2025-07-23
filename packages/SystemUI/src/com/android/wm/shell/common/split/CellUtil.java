package com.android.wm.shell.common.split;

import android.graphics.Rect;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CellUtil {
    public static void getCellAndHostArea(int i, Rect rect, Rect rect2, Rect rect3, boolean z) {
        if (isCellInLeftOrTopBounds(i, z)) {
            rect3.set(rect);
        } else {
            rect3.set(rect2);
        }
    }

    public static int getCellSide(int i, boolean z, boolean z2) {
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && z2) {
            if (z) {
                if ((i & 8) != 0) {
                    return 1;
                }
                return (i & 32) != 0 ? 3 : -1;
            }
            if ((i & 16) != 0) {
                return 2;
            }
            return (i & 64) != 0 ? 4 : -1;
        }
        if (z) {
            if ((i & 16) != 0) {
                return 2;
            }
            return (i & 64) != 0 ? 4 : -1;
        }
        if ((i & 8) != 0) {
            return 1;
        }
        return (i & 32) != 0 ? 3 : -1;
    }

    public static boolean isCellInLeftOrTopBounds(int i, boolean z) {
        if ((i & 16) == 0 || z) {
            return (i & 8) != 0 && z;
        }
        return true;
    }
}
