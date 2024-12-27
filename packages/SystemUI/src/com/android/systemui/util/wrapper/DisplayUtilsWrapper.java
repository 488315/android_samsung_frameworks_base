package com.android.systemui.util.wrapper;

import android.util.DisplayUtils;
import android.view.Display;

public final class DisplayUtilsWrapper {
    public static final int $stable = 0;

    public final Display.Mode getMaximumResolutionDisplayMode(Display.Mode[] modeArr) {
        return DisplayUtils.getMaximumResolutionDisplayMode(modeArr);
    }

    public final float getPhysicalPixelDisplaySizeRatio(int i, int i2, int i3, int i4) {
        return DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4);
    }
}
