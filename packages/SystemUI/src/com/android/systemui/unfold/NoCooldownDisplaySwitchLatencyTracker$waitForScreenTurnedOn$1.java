package com.android.systemui.unfold;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 extends ContinuationImpl {
    int I$0;
    long J$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NoCooldownDisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1(NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker, Continuation continuation) {
        super(continuation);
        this.this$0 = noCooldownDisplaySwitchLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NoCooldownDisplaySwitchLatencyTracker.access$waitForScreenTurnedOn(this.this$0, this);
    }
}
