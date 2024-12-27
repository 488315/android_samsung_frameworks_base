package com.android.systemui.statusbar.phone;

import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
