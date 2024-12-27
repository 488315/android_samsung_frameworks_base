package com.android.systemui.unfold;

import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class DisplaySwitchLatencyTracker$start$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WithPrev<DeviceStateRepository.DeviceState, DeviceStateRepository.DeviceState> $foldableDeviceState;
    int I$0;
    long J$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ DisplaySwitchLatencyTracker this$0;

    public DisplaySwitchLatencyTracker$start$1$2$1(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, WithPrev<? extends DeviceStateRepository.DeviceState, ? extends DeviceStateRepository.DeviceState> withPrev, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displaySwitchLatencyTracker;
        this.$foldableDeviceState = withPrev;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplaySwitchLatencyTracker$start$1$2$1 displaySwitchLatencyTracker$start$1$2$1 = new DisplaySwitchLatencyTracker$start$1$2$1(this.this$0, this.$foldableDeviceState, continuation);
        displaySwitchLatencyTracker$start$1$2$1.L$0 = obj;
        return displaySwitchLatencyTracker$start$1$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplaySwitchLatencyTracker$start$1$2$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r39) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
