package com.android.systemui.brightness.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ScreenBrightnessDisplayManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenBrightnessDisplayManagerRepository$getMinMaxLinearBrightness$1(ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = screenBrightnessDisplayManagerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getMinMaxLinearBrightness(this);
    }
}
