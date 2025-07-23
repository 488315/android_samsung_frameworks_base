package com.android.systemui.statusbar.phone;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
