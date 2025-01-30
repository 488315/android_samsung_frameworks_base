package com.android.systemui.statusbar.phone;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndicatorGardenAlgorithmNoCutout extends IndicatorGardenAlgorithm {
    public IndicatorGardenAlgorithmNoCutout(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        super(context, indicatorGardenInputProperties);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateCameraTopMargin() {
        return 0;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateCenterContainerMaxWidth() {
        return Math.max((int) ((this.inputProperties.statusBarWidth - (getDefaultSidePadding() * 2)) / 3.0f), 0);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftContainerMaxWidth(IndicatorGarden indicatorGarden) {
        return getLeftContainerMaxWidth(indicatorGarden);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftPadding() {
        return getDefaultSidePadding();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        return getRightContainerMaxWidth(indicatorGarden);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightPadding() {
        return getDefaultSidePadding();
    }
}
