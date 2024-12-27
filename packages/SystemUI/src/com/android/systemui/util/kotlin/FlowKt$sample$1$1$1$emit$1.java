package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.FlowKt$sample$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class FlowKt$sample$1$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt$sample$1.AnonymousClass1.C02791<Object> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$sample$1$1$1$emit$1(FlowKt$sample$1.AnonymousClass1.C02791<Object> c02791, Continuation continuation) {
        super(continuation);
        this.this$0 = c02791;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
