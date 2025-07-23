package com.android.wm.shell.windowdecor.extension;

import android.view.InsetsSource;
import android.view.InsetsState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class InsetsStateKt {
    public static final boolean isVisible(int i, InsetsState insetsState) {
        int sourceSize = insetsState.sourceSize();
        for (int i2 = 0; i2 < sourceSize; i2++) {
            InsetsSource sourceAt = insetsState.sourceAt(i2);
            if (sourceAt.getType() == i) {
                return sourceAt.isVisible();
            }
        }
        return false;
    }
}
