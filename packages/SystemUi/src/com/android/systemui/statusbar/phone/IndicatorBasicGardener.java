package com.android.systemui.statusbar.phone;

import android.util.Log;
import android.view.ViewGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        IndicatorGardenModel indicatorGardenModel2 = this.currentGardenModel;
        if (indicatorGardenModel2 != null && indicatorGardenModel.totalHeight == indicatorGardenModel2.totalHeight && indicatorGardenModel.paddingLeft == indicatorGardenModel2.paddingLeft && indicatorGardenModel.paddingRight == indicatorGardenModel2.paddingRight && indicatorGardenModel.hasCameraTopMargin == indicatorGardenModel2.hasCameraTopMargin && indicatorGardenModel.cameraTopMargin == indicatorGardenModel2.cameraTopMargin && indicatorGardenModel.maxWidthLeftContainer == indicatorGardenModel2.maxWidthLeftContainer && indicatorGardenModel.maxWidthCenterContainer == indicatorGardenModel2.maxWidthCenterContainer && indicatorGardenModel.maxWidthRightContainer == indicatorGardenModel2.maxWidthRightContainer) {
            return;
        }
        int i = indicatorGardenModel2.maxWidthCenterContainer;
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
        IndicatorGardenModel indicatorGardenModel3 = this.currentGardenModel;
        if (!(indicatorGardenModel3 != null && indicatorGardenModel.totalHeight == indicatorGardenModel3.totalHeight && indicatorGardenModel.hasCameraTopMargin == indicatorGardenModel3.hasCameraTopMargin && indicatorGardenModel.cameraTopMargin == indicatorGardenModel3.cameraTopMargin)) {
            int i7 = indicatorGardenModel.totalHeight;
            boolean z = indicatorGardenModel.hasCameraTopMargin;
            int i8 = indicatorGardenModel.cameraTopMargin;
            ViewGroup heightContainer = indicatorGarden.getHeightContainer();
            if (heightContainer != null) {
                ViewGroup.LayoutParams layoutParams = heightContainer.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = i7;
                }
                ViewGroup.MarginLayoutParams cameraTopMarginContainerMarginLayoutParams = getCameraTopMarginContainerMarginLayoutParams();
                if (!z || i8 <= 0) {
                    cameraTopMarginContainerMarginLayoutParams.height = i7;
                    cameraTopMarginContainerMarginLayoutParams.topMargin = 0;
                } else {
                    cameraTopMarginContainerMarginLayoutParams.height = i7 - i8;
                    cameraTopMarginContainerMarginLayoutParams.topMargin = i8;
                }
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
