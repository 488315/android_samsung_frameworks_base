package com.android.systemui.statusbar.window;

import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;

public final class StatusBarWindowControllerExt {
    public final int[] heights = {-1, -1, -1, -1};
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;

    public StatusBarWindowControllerExt(IndicatorCutoutUtil indicatorCutoutUtil) {
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
    }
}
