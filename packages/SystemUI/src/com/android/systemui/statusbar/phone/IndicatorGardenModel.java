package com.android.systemui.statusbar.phone;

import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IndicatorGardenModel {
    public int cameraTopMargin;
    public boolean hasCameraTopMargin;
    public int paddingLeft;
    public int paddingRight;
    public int totalHeight;
    public int maxWidthLeftContainer = -1;
    public int maxWidthCenterContainer = -1;
    public int maxWidthRightContainer = -1;

    public final boolean isEqual(IndicatorGardenModel indicatorGardenModel) {
        return indicatorGardenModel != null && this.totalHeight == indicatorGardenModel.totalHeight && this.paddingLeft == indicatorGardenModel.paddingLeft && this.paddingRight == indicatorGardenModel.paddingRight && this.hasCameraTopMargin == indicatorGardenModel.hasCameraTopMargin && this.cameraTopMargin == indicatorGardenModel.cameraTopMargin && this.maxWidthLeftContainer == indicatorGardenModel.maxWidthLeftContainer && this.maxWidthCenterContainer == indicatorGardenModel.maxWidthCenterContainer && this.maxWidthRightContainer == indicatorGardenModel.maxWidthRightContainer;
    }

    public final String toString() {
        int i = this.totalHeight;
        boolean z = this.hasCameraTopMargin;
        int i2 = this.cameraTopMargin;
        int i3 = this.paddingLeft;
        int i4 = this.paddingRight;
        int i5 = this.maxWidthLeftContainer;
        int i6 = this.maxWidthCenterContainer;
        int i7 = this.maxWidthRightContainer;
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("totalHeight=", i, "  hasCameraTopMargin=", z, "  cameraTopMargin=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i2, "  paddingLeft=", i3, "  paddingRight=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i4, "  maxWidthLeftContainer=", i5, "  maxWidthCenterContainer=");
        m.append(i6);
        m.append("  maxWidthRightContainer=");
        m.append(i7);
        return m.toString();
    }
}
