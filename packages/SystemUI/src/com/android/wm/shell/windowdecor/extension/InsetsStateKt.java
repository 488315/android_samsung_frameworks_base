package com.android.wm.shell.windowdecor.extension;

import android.view.InsetsSource;
import android.view.InsetsState;

/* loaded from: classes3.dex */
public abstract class InsetsStateKt {
    public static final boolean isVisible(int i, InsetsState insetsState) {
        int iSourceSize = insetsState.sourceSize();
        for (int i2 = 0; i2 < iSourceSize; i2++) {
            InsetsSource insetsSourceSourceAt = insetsState.sourceAt(i2);
            if (insetsSourceSourceAt.getType() == i) {
                return insetsSourceSourceAt.isVisible();
            }
        }
        return false;
    }
}
