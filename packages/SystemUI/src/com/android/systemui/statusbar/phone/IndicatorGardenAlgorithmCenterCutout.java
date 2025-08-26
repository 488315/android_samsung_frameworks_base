package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.DisplayCutout;
import com.android.systemui.util.DeviceType;

/* loaded from: classes3.dex */
public final class IndicatorGardenAlgorithmCenterCutout extends IndicatorGardenAlgorithm {
    public int cutoutCropSize;
    public int cutoutLeft;
    public int cutoutRight;
    public final IndicatorGardenInputProperties inputProperties;

    public IndicatorGardenAlgorithmCenterCutout(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        super(context, indicatorGardenInputProperties);
        this.inputProperties = indicatorGardenInputProperties;
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
        if (this.inputProperties.displayCutout != null) {
            return Math.abs(getCenterCutoutWidth());
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftContainerMaxWidth(IndicatorGarden indicatorGarden) {
        DisplayCutout displayCutout;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        return (getCenterCutoutWidth() <= 0 || (displayCutout = indicatorGardenInputProperties.displayCutout) == null || displayCutout.getSafeInsetTop() <= 0) ? getLeftContainerMaxWidth(indicatorGarden, getDefaultSidePadding(), getDefaultSidePadding()) : ((indicatorGardenInputProperties.statusBarWidth / 2) - calculateLeftPadding()) - (getCenterCutoutWidth() / 2);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftPadding() {
        int safeInsetLeft;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (indicatorGardenInputProperties.displayCutout == null || DeviceType.isTablet()) {
            safeInsetLeft = 0;
        } else {
            DisplayCutout displayCutout = indicatorGardenInputProperties.displayCutout;
            displayCutout.getClass();
            safeInsetLeft = displayCutout.getSafeInsetLeft();
        }
        return getDefaultSidePadding() + safeInsetLeft;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        DisplayCutout displayCutout;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        return (getCenterCutoutWidth() <= 0 || (displayCutout = indicatorGardenInputProperties.displayCutout) == null || displayCutout.getSafeInsetTop() <= 0) ? getRightContainerMaxWidth(indicatorGarden, getDefaultSidePadding(), getDefaultSidePadding()) : ((indicatorGardenInputProperties.statusBarWidth / 2) - calculateRightPadding()) - (getCenterCutoutWidth() / 2);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightPadding() {
        int safeInsetRight;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (indicatorGardenInputProperties.displayCutout == null || DeviceType.isTablet()) {
            safeInsetRight = 0;
        } else {
            DisplayCutout displayCutout = indicatorGardenInputProperties.displayCutout;
            displayCutout.getClass();
            safeInsetRight = displayCutout.getSafeInsetRight();
        }
        return getDefaultSidePadding() + safeInsetRight;
    }

    public final int getCenterCutoutWidth() {
        int i = this.cutoutCropSize;
        return i > 0 ? (this.cutoutRight - this.cutoutLeft) - i : (this.cutoutRight - this.cutoutLeft) + this.inputProperties.cutoutSidePaddingD;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final boolean hasCameraBottomMargin() {
        return getHasCutoutForIndicator();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final void initResources() {
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        this.cutoutCropSize = indicatorGardenInputProperties.cutoutInnerPaddingD;
        DisplayCutout displayCutout = indicatorGardenInputProperties.displayCutout;
        if (displayCutout != null) {
            this.cutoutLeft = displayCutout.getBoundingRectTop().left;
            this.cutoutRight = displayCutout.getBoundingRectTop().right;
        }
    }
}
