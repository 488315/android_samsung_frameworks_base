package com.android.systemui.shade;

import com.android.app.tracing.coroutines.TrackTracer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1 extends ContinuationImpl {
    int I$0;
    long J$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ShadeDisplayChangeLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1(ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker, Continuation continuation) {
        super(continuation);
        this.this$0 = shadeDisplayChangeLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker = this.this$0;
        TrackTracer trackTracer = ShadeDisplayChangeLatencyTracker.t;
        return shadeDisplayChangeLatencyTracker.waitUntilNextDoFrameDone(this);
    }
}
