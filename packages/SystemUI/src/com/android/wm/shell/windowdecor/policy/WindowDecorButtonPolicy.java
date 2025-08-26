package com.android.wm.shell.windowdecor.policy;

import android.content.res.Resources;

/* loaded from: classes3.dex */
public interface WindowDecorButtonPolicy {
    static int loadDimensionPixelSize(Resources resources, int i) {
        if (i == 0) {
            return 0;
        }
        return resources.getDimensionPixelSize(i);
    }
}
