package com.android.systemui.unfold;

import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NoCooldownDisplaySwitchLatencyTracker$start$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WithPrev<DeviceStateRepository.DeviceState, DeviceStateRepository.DeviceState> $foldableDeviceState;
    int I$0;
    long J$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ NoCooldownDisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NoCooldownDisplaySwitchLatencyTracker$start$1$2$1(NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker, WithPrev<? extends DeviceStateRepository.DeviceState, ? extends DeviceStateRepository.DeviceState> withPrev, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noCooldownDisplaySwitchLatencyTracker;
        this.$foldableDeviceState = withPrev;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoCooldownDisplaySwitchLatencyTracker$start$1$2$1 noCooldownDisplaySwitchLatencyTracker$start$1$2$1 = new NoCooldownDisplaySwitchLatencyTracker$start$1$2$1(this.this$0, this.$foldableDeviceState, continuation);
        noCooldownDisplaySwitchLatencyTracker$start$1$2$1.L$0 = obj;
        return noCooldownDisplaySwitchLatencyTracker$start$1$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoCooldownDisplaySwitchLatencyTracker$start$1$2$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x017f, code lost:
    
        if (r14.emit(r2, r41) != r1) goto L63;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0141  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r42) {
        /*
            Method dump skipped, instructions count: 389
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$start$1$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
