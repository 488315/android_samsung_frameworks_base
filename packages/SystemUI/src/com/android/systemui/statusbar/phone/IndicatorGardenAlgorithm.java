package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.DisplayCutout;
import com.android.systemui.R;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IndicatorGardenAlgorithm {
    public final Context context;
    public final float cornerRoundFromFloatingFeature;
    public final int cornerRoundSidePadding;
    public final IndicatorGardenInputProperties inputProperties;
    public final String name = getClass().getSimpleName();

    public IndicatorGardenAlgorithm(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        this.context = context;
        this.inputProperties = indicatorGardenInputProperties;
        float parseFloat = Float.parseFloat(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_CORNER_ROUND", "0.0"));
        this.cornerRoundFromFloatingFeature = parseFloat;
        this.cornerRoundSidePadding = (int) (((10.0f - parseFloat) * parseFloat) - 1.0f);
    }

    public int calculateCameraBottomMargin() {
        return 0;
    }

    public int calculateCameraTopMargin() {
        return this.context.getResources().getDimensionPixelSize(R.dimen.status_bar_basic_top_margin_without_cutout);
    }

    public int calculateCenterContainerMaxWidth() {
        return 0;
    }

    public int calculateLeftContainerMaxWidth(IndicatorGarden indicatorGarden) {
        return 0;
    }

    public int calculateLeftPadding() {
        return 0;
    }

    public int calculateRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        return 0;
    }

    public int calculateRightPadding() {
        return 0;
    }

    public final int getDefaultSidePadding() {
        int i;
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        return (!(indicatorGardenInputProperties.rotation == 0) || (i = indicatorGardenInputProperties.cornerPaddingC) == 0) ? Float.compare(this.cornerRoundFromFloatingFeature, 0.0f) != 0 ? (int) (this.cornerRoundSidePadding * indicatorGardenInputProperties.density) : indicatorGardenInputProperties.defaultStartPadding : i;
    }

    public final boolean getHasCutoutForIndicator() {
        DisplayCutout displayCutout = this.inputProperties.displayCutout;
        if (displayCutout == null) {
            return false;
        }
        displayCutout.getClass();
        return displayCutout.getSafeInsetTop() > 0;
    }

    public final int getLeftContainerMaxWidth(IndicatorGarden indicatorGarden) {
        int defaultSidePadding = getDefaultSidePadding();
        int defaultSidePadding2 = getDefaultSidePadding();
        IndicatorGardenContainer centerContainer = indicatorGarden.getCenterContainer();
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        if (centerContainer != null && centerContainer.isGardenVisible() && centerContainer.getGardenWidth() > 0) {
            return ((indicatorGardenInputProperties.statusBarWidth / 2) - defaultSidePadding) - (centerContainer.getGardenWidth() / 2);
        }
        IndicatorGardenContainer rightContainer = indicatorGarden.getRightContainer();
        return Math.max(((indicatorGardenInputProperties.statusBarWidth - Math.max(indicatorGarden.getEssentialRightWidth(), rightContainer != null ? rightContainer.getGardenWidth() : 0)) - (defaultSidePadding + defaultSidePadding2)) - indicatorGardenInputProperties.defaultCenterPadding, indicatorGarden.getEssentialLeftWidth());
    }

    public final int getRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        int defaultSidePadding = getDefaultSidePadding();
        int defaultSidePadding2 = getDefaultSidePadding();
        IndicatorGardenContainer centerContainer = indicatorGarden.getCenterContainer();
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        return (centerContainer == null || !centerContainer.isGardenVisible() || centerContainer.getGardenWidth() <= 0) ? Math.max(((indicatorGardenInputProperties.statusBarWidth - (defaultSidePadding + defaultSidePadding2)) - indicatorGarden.getEssentialLeftWidth()) - indicatorGardenInputProperties.defaultCenterPadding, indicatorGarden.getEssentialRightWidth()) : ((indicatorGardenInputProperties.statusBarWidth / 2) - defaultSidePadding2) - (centerContainer.getGardenWidth() / 2);
    }

    public boolean hasCameraBottomMargin() {
        return false;
    }

    public void initResources() {
    }
}
