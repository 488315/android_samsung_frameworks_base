package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class CameraQuickAffordanceConfig$getPickerScreenState$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CameraQuickAffordanceConfig this$0;

    public CameraQuickAffordanceConfig$getPickerScreenState$1(CameraQuickAffordanceConfig cameraQuickAffordanceConfig, Continuation continuation) {
        super(continuation);
        this.this$0 = cameraQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getPickerScreenState(this);
    }
}
