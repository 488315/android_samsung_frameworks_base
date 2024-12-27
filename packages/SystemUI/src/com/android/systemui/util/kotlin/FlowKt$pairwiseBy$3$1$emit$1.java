package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.FlowKt$pairwiseBy$3;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class FlowKt$pairwiseBy$3$1$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt$pairwiseBy$3.AnonymousClass1<Object> this$0;

    public FlowKt$pairwiseBy$3$1$emit$1(FlowKt$pairwiseBy$3.AnonymousClass1<Object> anonymousClass1, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
