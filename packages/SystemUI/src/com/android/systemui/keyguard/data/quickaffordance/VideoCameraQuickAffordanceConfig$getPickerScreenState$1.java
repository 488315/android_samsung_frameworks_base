package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class VideoCameraQuickAffordanceConfig$getPickerScreenState$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ VideoCameraQuickAffordanceConfig this$0;

    public VideoCameraQuickAffordanceConfig$getPickerScreenState$1(VideoCameraQuickAffordanceConfig videoCameraQuickAffordanceConfig, Continuation continuation) {
        super(continuation);
        this.this$0 = videoCameraQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getPickerScreenState(this);
    }
}
