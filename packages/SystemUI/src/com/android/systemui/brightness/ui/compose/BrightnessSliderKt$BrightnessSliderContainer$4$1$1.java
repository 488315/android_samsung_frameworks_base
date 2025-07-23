package com.android.systemui.brightness.ui.compose;

import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class BrightnessSliderKt$BrightnessSliderContainer$4$1$1 extends FunctionReferenceImpl implements Function1 {
    public BrightnessSliderKt$BrightnessSliderContainer$4$1$1(Object obj) {
        super(1, obj, BrightnessSliderViewModel.Companion.class, "getIconForPercentage", "getIconForPercentage(F)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        float floatValue = ((Number) obj).floatValue();
        ((BrightnessSliderViewModel.Companion) this.receiver).getClass();
        return Integer.valueOf(floatValue <= 20.0f ? BrightnessSliderViewModel.icons.brightnessLow : floatValue >= 80.0f ? BrightnessSliderViewModel.icons.brightnessHigh : BrightnessSliderViewModel.icons.brightnessMid);
    }
}
