package com.android.systemui.statusbar.phone;

import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public final class IndicatorGardenModel {
    public int cameraBottomMargin;
    public int cameraTopMargin;
    public boolean hasCameraBottomMargin;
    public boolean hasCameraTopMargin;
    public int paddingLeft;
    public int paddingRight;
    public int totalHeight;
    public int maxWidthLeftContainer = -1;
    public int maxWidthCenterContainer = -1;
    public int maxWidthRightContainer = -1;

    public final boolean isEqual(IndicatorGardenModel indicatorGardenModel) {
        return indicatorGardenModel != null && this.totalHeight == indicatorGardenModel.totalHeight && this.paddingLeft == indicatorGardenModel.paddingLeft && this.paddingRight == indicatorGardenModel.paddingRight && this.hasCameraTopMargin == indicatorGardenModel.hasCameraTopMargin && this.cameraTopMargin == indicatorGardenModel.cameraTopMargin && this.hasCameraBottomMargin == indicatorGardenModel.hasCameraBottomMargin && this.cameraBottomMargin == indicatorGardenModel.cameraBottomMargin && this.maxWidthLeftContainer == indicatorGardenModel.maxWidthLeftContainer && this.maxWidthCenterContainer == indicatorGardenModel.maxWidthCenterContainer && this.maxWidthRightContainer == indicatorGardenModel.maxWidthRightContainer;
    }

    public final String toString() {
        int i = this.totalHeight;
        boolean z = this.hasCameraTopMargin;
        int i2 = this.cameraTopMargin;
        boolean z2 = this.hasCameraBottomMargin;
        int i3 = this.cameraBottomMargin;
        int i4 = this.paddingLeft;
        int i5 = this.paddingRight;
        int i6 = this.maxWidthLeftContainer;
        int i7 = this.maxWidthCenterContainer;
        int i8 = this.maxWidthRightContainer;
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("totalHeight=", i, "  hasCameraTopMargin=", z, "  cameraTopMargin=");
        sbM.append(i2);
        sbM.append("  hasCameraBottomMargin=");
        sbM.append(z2);
        sbM.append("  cameraBottomMargin=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i3, "  paddingLeft=", i4, "  paddingRight=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i5, "  maxWidthLeftContainer=", i6, "  maxWidthCenterContainer=");
        sbM.append(i7);
        sbM.append("  maxWidthRightContainer=");
        sbM.append(i8);
        return sbM.toString();
    }
}
