package com.android.systemui.statusbar.phone;

import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndicatorGardenModel {
    public int cameraTopMargin;
    public boolean hasCameraTopMargin;
    public int paddingLeft;
    public int paddingRight;
    public int totalHeight;
    public int maxWidthLeftContainer = -1;
    public int maxWidthCenterContainer = -1;
    public int maxWidthRightContainer = -1;

    public final String toString() {
        int i = this.totalHeight;
        boolean z = this.hasCameraTopMargin;
        int i2 = this.cameraTopMargin;
        int i3 = this.paddingLeft;
        int i4 = this.paddingRight;
        int i5 = this.maxWidthLeftContainer;
        int i6 = this.maxWidthCenterContainer;
        int i7 = this.maxWidthRightContainer;
        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("height=", i, " hasCameraTopMargin=", z, " cameraTopMargin=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m76m, i2, "paddingLeft=", i3, " paddingRight=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m76m, i4, " maxWidthLeftContainer=", i5, " maxWidthCenterContainer=");
        m76m.append(i6);
        m76m.append(" maxWidthRightContainer=");
        m76m.append(i7);
        return m76m.toString();
    }
}
