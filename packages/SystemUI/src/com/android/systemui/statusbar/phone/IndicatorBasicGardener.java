package com.android.systemui.statusbar.phone;

import android.util.Log;
import android.view.ViewGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IndicatorBasicGardener {
    public IndicatorGardenModel currentGardenModel = new IndicatorGardenModel();
    public final String gardenName;
    public final IndicatorGarden gardenView;

    public IndicatorBasicGardener(IndicatorGarden indicatorGarden, String str) {
        this.gardenView = indicatorGarden;
        this.gardenName = str;
    }

    public abstract ViewGroup.MarginLayoutParams getCameraTopMarginContainerMarginLayoutParams();

    public boolean needToUpdatePaddings(IndicatorGardenModel indicatorGardenModel) {
        IndicatorGardenModel indicatorGardenModel2 = this.currentGardenModel;
        return (indicatorGardenModel2.paddingLeft == indicatorGardenModel.paddingLeft && indicatorGardenModel2.paddingRight == indicatorGardenModel.paddingRight) ? false : true;
    }

    public final void updateGarden(IndicatorGardenModel indicatorGardenModel, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        IndicatorGardenContainer leftContainer;
        IndicatorGardenContainer rightContainer;
        IndicatorGardenContainer centerContainer;
        if (needToUpdatePaddings(indicatorGardenModel)) {
            updateSidePadding(indicatorGardenModel.paddingLeft, indicatorGardenModel.paddingRight);
        }
        if (indicatorGardenModel.isEqual(this.currentGardenModel)) {
            return;
        }
        int i = this.currentGardenModel.maxWidthCenterContainer;
        int i2 = indicatorGardenModel.maxWidthCenterContainer;
        IndicatorGarden indicatorGarden = this.gardenView;
        if (i != i2 && (centerContainer = indicatorGarden.getCenterContainer()) != null) {
            centerContainer.setGardenMaxWidth(i2);
        }
        int i3 = this.currentGardenModel.maxWidthRightContainer;
        int i4 = indicatorGardenModel.maxWidthRightContainer;
        if (i3 != i4 && (rightContainer = indicatorGarden.getRightContainer()) != null) {
            rightContainer.setGardenMaxWidth(i4);
        }
        int i5 = this.currentGardenModel.maxWidthLeftContainer;
        int i6 = indicatorGardenModel.maxWidthLeftContainer;
        if (i5 != i6 && (leftContainer = indicatorGarden.getLeftContainer()) != null) {
            leftContainer.setGardenMaxWidth(i6);
        }
        IndicatorGardenModel indicatorGardenModel2 = this.currentGardenModel;
        if (indicatorGardenModel2 == null || indicatorGardenModel.totalHeight != indicatorGardenModel2.totalHeight || indicatorGardenModel.hasCameraTopMargin != indicatorGardenModel2.hasCameraTopMargin || indicatorGardenModel.cameraTopMargin != indicatorGardenModel2.cameraTopMargin || indicatorGardenModel.hasCameraBottomMargin != indicatorGardenModel2.hasCameraBottomMargin || indicatorGardenModel.cameraBottomMargin != indicatorGardenModel2.cameraBottomMargin) {
            int i7 = indicatorGardenModel.totalHeight;
            boolean z = indicatorGardenModel.hasCameraTopMargin;
            int i8 = indicatorGardenModel.cameraTopMargin;
            boolean z2 = indicatorGardenModel.hasCameraBottomMargin;
            int i9 = indicatorGardenModel.cameraBottomMargin;
            ViewGroup heightContainer = indicatorGarden.getHeightContainer();
            if (heightContainer != null) {
                ViewGroup.LayoutParams layoutParams = heightContainer.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = i7;
                }
                if (!z || i8 <= 0) {
                    i8 = 0;
                }
                if (!z2 || i9 <= 0) {
                    i9 = 0;
                }
                ViewGroup.MarginLayoutParams cameraTopMarginContainerMarginLayoutParams = getCameraTopMarginContainerMarginLayoutParams();
                cameraTopMarginContainerMarginLayoutParams.height = (i7 - i8) - i9;
                cameraTopMarginContainerMarginLayoutParams.topMargin = i8;
                cameraTopMarginContainerMarginLayoutParams.bottomMargin = i9;
            }
        }
        this.currentGardenModel = indicatorGardenModel;
        Log.d("IndicatorBasicGardener", this.gardenName + " update is done " + indicatorGardenModel + " cutout=" + indicatorGardenInputProperties.displayCutout);
    }

    public void updateSidePadding(int i, int i2) {
        ViewGroup sidePaddingContainer = this.gardenView.getSidePaddingContainer();
        if (sidePaddingContainer != null) {
            sidePaddingContainer.setPadding(i, 0, i2, 0);
        }
    }
}
