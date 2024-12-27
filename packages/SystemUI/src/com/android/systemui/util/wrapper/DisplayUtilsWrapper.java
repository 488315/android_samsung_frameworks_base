package com.android.systemui.util.wrapper;

import android.util.DisplayUtils;
import android.view.Display;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisplayUtilsWrapper {
    public static final int $stable = 0;

    public final Display.Mode getMaximumResolutionDisplayMode(Display.Mode[] modeArr) {
        return DisplayUtils.getMaximumResolutionDisplayMode(modeArr);
    }

    public final float getPhysicalPixelDisplaySizeRatio(int i, int i2, int i3, int i4) {
        return DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4);
    }
}
