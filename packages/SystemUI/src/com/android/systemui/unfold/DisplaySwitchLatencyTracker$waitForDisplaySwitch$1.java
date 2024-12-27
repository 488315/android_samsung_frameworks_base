package com.android.systemui.unfold;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class DisplaySwitchLatencyTracker$waitForDisplaySwitch$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplaySwitchLatencyTracker$waitForDisplaySwitch$1(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, Continuation continuation) {
        super(continuation);
        this.this$0 = displaySwitchLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DisplaySwitchLatencyTracker.access$waitForDisplaySwitch(this.this$0, 0, this);
    }
}
