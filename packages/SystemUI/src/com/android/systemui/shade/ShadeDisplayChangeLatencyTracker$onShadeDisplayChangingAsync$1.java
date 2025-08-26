package com.android.systemui.shade;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ShadeDisplayChangeLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1(ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker, Continuation continuation) {
        super(continuation);
        this.this$0 = shadeDisplayChangeLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ShadeDisplayChangeLatencyTracker.access$onShadeDisplayChangingAsync(this.this$0, 0, this);
    }
}
