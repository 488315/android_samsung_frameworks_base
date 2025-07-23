package com.android.systemui.brightness.ui.compose;

import android.content.Context;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class BrightnessSliderKt$BrightnessSliderContainer$4$2$1 extends FunctionReferenceImpl implements Function3 {
    public BrightnessSliderKt$BrightnessSliderContainer$4$2$1(Object obj) {
        super(3, obj, BrightnessSliderViewModel.class, "loadImage", "loadImage(ILandroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((BrightnessSliderViewModel) this.receiver).loadImage(((Number) obj).intValue(), (Context) obj2, (Continuation) obj3);
    }
}
