package com.android.systemui.unfold;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, Continuation continuation) {
        super(continuation);
        this.this$0 = displaySwitchLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DisplaySwitchLatencyTracker.access$waitForGoToSleepWithScreenOff(this.this$0, this);
    }
}
