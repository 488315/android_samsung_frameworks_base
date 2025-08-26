package com.android.systemui.brightness.ui.compose;

import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class BrightnessSliderKt$BrightnessSliderContainer$4$1$1 extends FunctionReferenceImpl implements Function1 {
    public BrightnessSliderKt$BrightnessSliderContainer$4$1$1(Object obj) {
        super(1, obj, BrightnessSliderViewModel.Companion.class, "getIconForPercentage", "getIconForPercentage(F)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        float fFloatValue = ((Number) obj).floatValue();
        ((BrightnessSliderViewModel.Companion) this.receiver).getClass();
        return Integer.valueOf(fFloatValue <= 20.0f ? BrightnessSliderViewModel.icons.brightnessLow : fFloatValue >= 80.0f ? BrightnessSliderViewModel.icons.brightnessHigh : BrightnessSliderViewModel.icons.brightnessMid);
    }
}
