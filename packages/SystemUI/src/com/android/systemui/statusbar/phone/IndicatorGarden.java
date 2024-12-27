package com.android.systemui.statusbar.phone;

import android.view.ViewGroup;
import android.view.WindowInsets;

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
