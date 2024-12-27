package com.android.systemui.statusbar.phone;

import android.view.ViewGroup;
import android.view.WindowInsets;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface IndicatorGarden {
    IndicatorGardenContainer getCenterContainer();

    int getEssentialLeftWidth();

    int getEssentialRightWidth();

    WindowInsets getGardenWindowInsets();

    ViewGroup getHeightContainer();

    IndicatorGardenContainer getLeftContainer();

    IndicatorGardenContainer getRightContainer();

    ViewGroup getSidePaddingContainer();

    void updateGarden(IndicatorGardenModel indicatorGardenModel, IndicatorGardenInputProperties indicatorGardenInputProperties);
}
