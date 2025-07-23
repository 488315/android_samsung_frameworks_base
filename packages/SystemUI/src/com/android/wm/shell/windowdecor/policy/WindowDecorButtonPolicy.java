package com.android.wm.shell.windowdecor.policy;

import android.content.res.Resources;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface WindowDecorButtonPolicy {
    static int loadDimensionPixelSize(Resources resources, int i) {
        if (i == 0) {
            return 0;
        }
        return resources.getDimensionPixelSize(i);
    }
}
