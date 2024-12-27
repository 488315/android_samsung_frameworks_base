package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.DisplayCutout;
import kotlin.jvm.internal.Intrinsics;

public final class IndicatorGardenAlgorithmSidelingCenterCutout extends IndicatorGardenAlgorithm {
    public int cutoutLeft;
    public int cutoutRight;
    public final IndicatorCutoutUtil indicatorCutoutUtil;
    public final IndicatorGardenInputProperties inputProperties;

    public IndicatorGardenAlgorithmSidelingCenterCutout(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties, IndicatorCutoutUtil indicatorCutoutUtil) {
        super(context, indicatorGardenInputProperties);
        this.inputProperties = indicatorGardenInputProperties;
        this.indicatorCutoutUtil = indicatorCutoutUtil;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateCameraTopMargin() {
        if (hasCameraTopMargin()) {
            return this.inputProperties.cutoutTopMarginB;
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateCenterContainerMaxWidth() {
        int i = this.inputProperties.statusBarWidth;
        if (i > 0) {
            return i / 4;
        }
        return Math.max((int) ((super.inputProperties.statusBarWidth - (getDefaultSidePadding() * 2)) / 3.0f), 0);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftContainerMaxWidth(IndicatorGarden indicatorGarden) {
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (indicatorGardenInputProperties.isRTL()) {
            int i = indicatorGardenInputProperties.statusBarWidth;
            IndicatorGardenContainer centerContainer = indicatorGarden.getCenterContainer();
            int calculateRightPadding = (i / 2) - calculateRightPadding();
            int gardenWidth = (centerContainer == null || !centerContainer.isGardenVisible() || centerContainer.getGardenWidth() <= 0) ? calculateRightPadding : calculateRightPadding - (centerContainer.getGardenWidth() / 2);
            int i2 = i - this.cutoutRight;
            return (i2 <= 0 || i2 <= calculateRightPadding()) ? Math.min(gardenWidth, calculateRightPadding) : Math.min(gardenWidth, i2 - calculateRightPadding());
        }
        if (!getHasCutoutForIndicator()) {
            return getLeftContainerMaxWidth(indicatorGarden);
        }
        int calculateLeftPadding = this.cutoutLeft - calculateLeftPadding();
        int calculateRightPadding2 = (indicatorGardenInputProperties.statusBarWidth - calculateRightPadding()) - calculateLeftPadding();
        IndicatorGardenContainer rightContainer = indicatorGarden.getRightContainer();
        Intrinsics.checkNotNull(rightContainer);
        return Math.min(calculateLeftPadding, calculateRightPadding2 - rightContainer.getGardenWidth());
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftPadding() {
        if (this.indicatorCutoutUtil.isUDCMainDisplay()) {
            return getDefaultSidePadding();
        }
        DisplayCutout displayCutout = this.inputProperties.displayCutout;
        return getDefaultSidePadding() + (displayCutout != null ? displayCutout.getSafeInsetLeft() : 0);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (!indicatorGardenInputProperties.isRTL()) {
            return getRightContainerMaxWidth(indicatorGarden);
        }
        IndicatorGardenContainer centerContainer = indicatorGarden.getCenterContainer();
        int calculateLeftPadding = (indicatorGardenInputProperties.statusBarWidth / 2) - (getHasCutoutForIndicator() ? calculateLeftPadding() : 0);
        int gardenWidth = (centerContainer == null || !centerContainer.isGardenVisible() || centerContainer.getGardenWidth() <= 0) ? calculateLeftPadding : calculateLeftPadding - (centerContainer.getGardenWidth() / 2);
        int calculateRightPadding = (indicatorGardenInputProperties.statusBarWidth - calculateRightPadding()) - calculateLeftPadding();
        IndicatorGardenContainer rightContainer = indicatorGarden.getRightContainer();
        Intrinsics.checkNotNull(rightContainer);
        int gardenWidth2 = calculateRightPadding - rightContainer.getGardenWidth();
        return gardenWidth2 > 0 ? Math.min(gardenWidth, gardenWidth2) : Math.min(gardenWidth, calculateLeftPadding);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightPadding() {
        if (this.indicatorCutoutUtil.isUDCMainDisplay()) {
            return getDefaultSidePadding();
        }
        DisplayCutout displayCutout = this.inputProperties.displayCutout;
        return getDefaultSidePadding() + (displayCutout != null ? displayCutout.getSafeInsetRight() : 0);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final boolean hasCameraTopMargin() {
        return super.inputProperties.rotation == 0 && getHasCutoutForIndicator();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final void initResources() {
        DisplayCutout displayCutout = this.inputProperties.displayCutout;
        if (displayCutout != null) {
            this.cutoutLeft = displayCutout.getBoundingRectTop().left;
            this.cutoutRight = displayCutout.getBoundingRectTop().right;
        }
    }
}
