package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.FlowKt$throttle$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class FlowKt$throttle$1$1$1$emit$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt$throttle$1.AnonymousClass1.C04151<Object> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$throttle$1$1$1$emit$1(FlowKt$throttle$1.AnonymousClass1.C04151<Object> c04151, Continuation continuation) {
        super(continuation);
        this.this$0 = c04151;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
