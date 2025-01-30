package com.android.systemui.statusbar.phone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
