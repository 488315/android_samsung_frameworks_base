package com.android.systemui.statusbar.phone;

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
