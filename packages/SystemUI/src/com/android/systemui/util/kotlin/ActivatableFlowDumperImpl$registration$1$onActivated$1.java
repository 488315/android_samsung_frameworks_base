package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class ActivatableFlowDumperImpl$registration$1$onActivated$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActivatableFlowDumperImpl$registration$1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivatableFlowDumperImpl$registration$1$onActivated$1(ActivatableFlowDumperImpl$registration$1 activatableFlowDumperImpl$registration$1, Continuation continuation) {
        super(continuation);
        this.this$0 = activatableFlowDumperImpl$registration$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.onActivated(this);
    }
}
