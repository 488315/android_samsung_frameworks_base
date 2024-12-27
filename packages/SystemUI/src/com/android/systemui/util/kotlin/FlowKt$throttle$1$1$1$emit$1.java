package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.FlowKt$throttle$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class FlowKt$throttle$1$1$1$emit$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt$throttle$1.AnonymousClass1.C02801<Object> this$0;

    public FlowKt$throttle$1$1$1$emit$1(FlowKt$throttle$1.AnonymousClass1.C02801<Object> c02801, Continuation continuation) {
        super(continuation);
        this.this$0 = c02801;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
