package com.android.systemui.brightness.ui.compose;

import android.content.Context;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

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
