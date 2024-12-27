package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.FlowKt$throttle$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class FlowKt$throttle$1$1$1$emit$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt$throttle$1.AnonymousClass1.C02801<Object> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
