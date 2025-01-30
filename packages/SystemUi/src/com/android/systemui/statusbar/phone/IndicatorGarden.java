package com.android.systemui.statusbar.phone;

import android.view.ViewGroup;
import android.view.WindowInsets;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
