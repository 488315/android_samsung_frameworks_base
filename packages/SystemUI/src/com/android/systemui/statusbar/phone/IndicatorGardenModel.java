package com.android.systemui.statusbar.phone;

import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("totalHeight=", i, "  hasCameraTopMargin=", z, "  cameraTopMargin=");
        m.append(i2);
        m.append("  hasCameraBottomMargin=");
        m.append(z2);
        m.append("  cameraBottomMargin=");
        ViewPager$$ExternalSyntheticOutline0.m(m, i3, "  paddingLeft=", i4, "  paddingRight=");
        ViewPager$$ExternalSyntheticOutline0.m(m, i5, "  maxWidthLeftContainer=", i6, "  maxWidthCenterContainer=");
        m.append(i7);
        m.append("  maxWidthRightContainer=");
        m.append(i8);
        return m.toString();
    }
}
