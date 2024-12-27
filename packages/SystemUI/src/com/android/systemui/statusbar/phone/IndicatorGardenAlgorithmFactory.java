package com.android.systemui.statusbar.phone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IndicatorGardenAlgorithmFactory {
    public final IndicatorCutoutUtil indicatorCutoutUtil;
    public final IndicatorGardenAlgorithmCenterCutout indicatorGardenAlgorithmCenterCutout;
    public final IndicatorGardenAlgorithmNoCutout indicatorGardenAlgorithmNoCutout;
    public final IndicatorGardenAlgorithmSidelingCenterCutout indicatorGardenAlgorithmSidelingCenterCutout;

    public IndicatorGardenAlgorithmFactory(IndicatorCutoutUtil indicatorCutoutUtil, IndicatorGardenAlgorithmNoCutout indicatorGardenAlgorithmNoCutout, IndicatorGardenAlgorithmCenterCutout indicatorGardenAlgorithmCenterCutout, IndicatorGardenAlgorithmSidelingCenterCutout indicatorGardenAlgorithmSidelingCenterCutout) {
        this.indicatorCutoutUtil = indicatorCutoutUtil;
        this.indicatorGardenAlgorithmNoCutout = indicatorGardenAlgorithmNoCutout;
        this.indicatorGardenAlgorithmCenterCutout = indicatorGardenAlgorithmCenterCutout;
        this.indicatorGardenAlgorithmSidelingCenterCutout = indicatorGardenAlgorithmSidelingCenterCutout;
    }
}
