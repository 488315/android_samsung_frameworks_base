package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.DisplayCutout;

/* loaded from: classes3.dex */
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
    public final int calculateCameraBottomMargin() {
        if (getHasCutoutForIndicator()) {
            return this.inputProperties.cutoutBottomMarginGb;
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateCameraTopMargin() {
        return getHasCutoutForIndicator() ? this.inputProperties.cutoutTopMarginB : super.calculateCameraTopMargin();
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
            int iCalculateRightPadding = (i / 2) - calculateRightPadding();
            int gardenWidth = (centerContainer == null || !centerContainer.isGardenVisible() || centerContainer.getGardenWidth() <= 0) ? iCalculateRightPadding : iCalculateRightPadding - (centerContainer.getGardenWidth() / 2);
            int i2 = i - this.cutoutRight;
            return (i2 <= 0 || i2 <= calculateRightPadding()) ? Math.min(gardenWidth, iCalculateRightPadding) : Math.min(gardenWidth, i2 - calculateRightPadding());
        }
        if (!getHasCutoutForIndicator()) {
            return getLeftContainerMaxWidth(indicatorGarden, calculateLeftPadding(), calculateRightPadding());
        }
        int iCalculateLeftPadding = this.cutoutLeft - calculateLeftPadding();
        int iCalculateRightPadding2 = (indicatorGardenInputProperties.statusBarWidth - calculateRightPadding()) - calculateLeftPadding();
        IndicatorGardenContainer rightContainer = indicatorGarden.getRightContainer();
        rightContainer.getClass();
        return Math.min(iCalculateLeftPadding, iCalculateRightPadding2 - rightContainer.getGardenWidth());
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftPadding() {
        if (this.indicatorCutoutUtil.isUDCMainDisplay()) {
            return getDefaultSidePadding();
        }
        DisplayCutout displayCutout = this.inputProperties.displayCutout;
        int safeInsetLeft = displayCutout != null ? displayCutout.getSafeInsetLeft() : 0;
        return safeInsetLeft > 0 ? (getDefaultSidePadding() / 3) + safeInsetLeft : getDefaultSidePadding();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (!indicatorGardenInputProperties.isRTL()) {
            return getRightContainerMaxWidth(indicatorGarden, calculateLeftPadding(), calculateRightPadding());
        }
        IndicatorGardenContainer centerContainer = indicatorGarden.getCenterContainer();
        int iCalculateLeftPadding = (indicatorGardenInputProperties.statusBarWidth / 2) - (getHasCutoutForIndicator() ? calculateLeftPadding() : 0);
        int gardenWidth = (centerContainer == null || !centerContainer.isGardenVisible() || centerContainer.getGardenWidth() <= 0) ? iCalculateLeftPadding : iCalculateLeftPadding - (centerContainer.getGardenWidth() / 2);
        int iCalculateRightPadding = (indicatorGardenInputProperties.statusBarWidth - calculateRightPadding()) - calculateLeftPadding();
        IndicatorGardenContainer rightContainer = indicatorGarden.getRightContainer();
        rightContainer.getClass();
        int gardenWidth2 = iCalculateRightPadding - rightContainer.getGardenWidth();
        return gardenWidth2 > 0 ? Math.min(gardenWidth, gardenWidth2) : Math.min(gardenWidth, iCalculateLeftPadding);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightPadding() {
        if (this.indicatorCutoutUtil.isUDCMainDisplay()) {
            return getDefaultSidePadding();
        }
        DisplayCutout displayCutout = this.inputProperties.displayCutout;
        int safeInsetRight = displayCutout != null ? displayCutout.getSafeInsetRight() : 0;
        return safeInsetRight > 0 ? (getDefaultSidePadding() / 3) + safeInsetRight : getDefaultSidePadding();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final boolean hasCameraBottomMargin() {
        return getHasCutoutForIndicator();
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
