package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.DisplayCutout;
import com.android.systemui.util.DeviceType;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final int calculateCameraTopMargin() {
        if (hasCameraTopMargin()) {
            return this.inputProperties.cutoutTopMarginB;
        }
        return 0;
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
        int i = indicatorGardenInputProperties.statusBarWidth;
        if (getCenterCutoutWidth() > 0 && (displayCutout = indicatorGardenInputProperties.displayCutout) != null) {
            Intrinsics.checkNotNull(displayCutout);
            if (displayCutout.getSafeInsetTop() > 0) {
                return ((i / 2) - calculateLeftPadding()) - (getCenterCutoutWidth() / 2);
            }
        }
        return getLeftContainerMaxWidth(indicatorGarden);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftPadding() {
        int i;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (indicatorGardenInputProperties.displayCutout == null || DeviceType.isTablet()) {
            i = 0;
        } else {
            DisplayCutout displayCutout = indicatorGardenInputProperties.displayCutout;
            Intrinsics.checkNotNull(displayCutout);
            i = displayCutout.getSafeInsetLeft();
        }
        return getDefaultSidePadding() + i;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        DisplayCutout displayCutout;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        int i = indicatorGardenInputProperties.statusBarWidth;
        if (getCenterCutoutWidth() > 0 && (displayCutout = indicatorGardenInputProperties.displayCutout) != null) {
            Intrinsics.checkNotNull(displayCutout);
            if (displayCutout.getSafeInsetTop() > 0) {
                return ((i / 2) - calculateRightPadding()) - (getCenterCutoutWidth() / 2);
            }
        }
        return getRightContainerMaxWidth(indicatorGarden);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightPadding() {
        int i;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (indicatorGardenInputProperties.displayCutout == null || DeviceType.isTablet()) {
            i = 0;
        } else {
            DisplayCutout displayCutout = indicatorGardenInputProperties.displayCutout;
            Intrinsics.checkNotNull(displayCutout);
            i = displayCutout.getSafeInsetRight();
        }
        return getDefaultSidePadding() + i;
    }

    public final int getCenterCutoutWidth() {
        int i = this.cutoutCropSize;
        return i > 0 ? (this.cutoutRight - this.cutoutLeft) - i : (this.cutoutRight - this.cutoutLeft) + this.inputProperties.cutoutSidePaddingD;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final boolean hasCameraTopMargin() {
        return getHasCutoutForIndicator() && super.inputProperties.rotation == 0;
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
