package com.android.systemui.statusbar.phone;

import com.android.systemui.BasicRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final IndicatorGardenAlgorithm makeAlgorithm() {
        IndicatorCutoutUtil indicatorCutoutUtil = this.indicatorCutoutUtil;
        CutoutType cutoutType = indicatorCutoutUtil.cutoutType;
        CutoutType cutoutType2 = CutoutType.SIDELING_CENTER_CUTOUT;
        IndicatorGardenAlgorithmSidelingCenterCutout indicatorGardenAlgorithmSidelingCenterCutout = this.indicatorGardenAlgorithmSidelingCenterCutout;
        return cutoutType == cutoutType2 ? indicatorGardenAlgorithmSidelingCenterCutout : cutoutType == CutoutType.CENTER_CUTOUT ? this.indicatorGardenAlgorithmCenterCutout : (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && indicatorCutoutUtil.isMainDisplay()) ? indicatorGardenAlgorithmSidelingCenterCutout : this.indicatorGardenAlgorithmNoCutout;
    }
}
