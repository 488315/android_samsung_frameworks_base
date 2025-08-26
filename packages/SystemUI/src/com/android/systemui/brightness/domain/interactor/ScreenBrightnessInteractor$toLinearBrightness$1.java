package com.android.systemui.brightness.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
final class ScreenBrightnessInteractor$toLinearBrightness$1 extends ContinuationImpl {
    int I$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ScreenBrightnessInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenBrightnessInteractor$toLinearBrightness$1(ScreenBrightnessInteractor screenBrightnessInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = screenBrightnessInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        ScreenBrightnessInteractor screenBrightnessInteractor = this.this$0;
        int i = ScreenBrightnessInteractor.$r8$clinit;
        return screenBrightnessInteractor.m1065toLinearBrightnesskRMD4pI(0, this);
    }
}
