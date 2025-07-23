package com.android.systemui.util.wrapper;

import android.util.DisplayUtils;
import android.view.Display;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisplayUtilsWrapper {
    public static final int $stable = 0;

    public final Display.Mode getMaximumResolutionDisplayMode(Display.Mode[] modeArr) {
        return DisplayUtils.getMaximumResolutionDisplayMode(modeArr);
    }

    public final float getPhysicalPixelDisplaySizeRatio(int i, int i2, int i3, int i4) {
        return DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4);
    }
}
