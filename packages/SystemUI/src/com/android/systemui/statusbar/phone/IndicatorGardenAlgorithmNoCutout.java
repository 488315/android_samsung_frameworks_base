package com.android.systemui.statusbar.phone;

import android.content.Context;

/* loaded from: classes3.dex */
public final class IndicatorGardenAlgorithmNoCutout extends IndicatorGardenAlgorithm {
    public IndicatorGardenAlgorithmNoCutout(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        super(context, indicatorGardenInputProperties);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateCenterContainerMaxWidth() {
        return Math.max((int) ((this.inputProperties.statusBarWidth - (getDefaultSidePadding() * 2)) / 3.0f), 0);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftContainerMaxWidth(IndicatorGarden indicatorGarden) {
        return getLeftContainerMaxWidth(indicatorGarden, getDefaultSidePadding(), getDefaultSidePadding());
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateLeftPadding() {
        return getDefaultSidePadding();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightContainerMaxWidth(IndicatorGarden indicatorGarden) {
        return getRightContainerMaxWidth(indicatorGarden, getDefaultSidePadding(), getDefaultSidePadding());
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenAlgorithm
    public final int calculateRightPadding() {
        return getDefaultSidePadding();
    }
}
