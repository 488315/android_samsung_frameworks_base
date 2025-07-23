package com.android.systemui.unfold;

import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DisplaySwitchLatencyTracker$startCoolDown$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent $event;
    long J$0;
    Object L$0;
    int label;
    final /* synthetic */ DisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplaySwitchLatencyTracker$startCoolDown$1(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displaySwitchLatencyTracker;
        this.$event = displaySwitchLatencyEvent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DisplaySwitchLatencyTracker$startCoolDown$1(this.this$0, this.$event, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplaySwitchLatencyTracker$startCoolDown$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0071  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            if (r1 == 0) goto L1c
            if (r1 != r2) goto L14
            long r0 = r11.J$0
            java.lang.Object r2 = r11.L$0
            kotlin.jvm.internal.Ref$ObjectRef r2 = (kotlin.jvm.internal.Ref$ObjectRef) r2
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4f
            goto L90
        L14:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1c:
            kotlin.ResultKt.throwOnFailure(r12)
            com.android.systemui.unfold.DisplaySwitchLatencyTracker r12 = r11.this$0
            com.android.systemui.util.time.SystemClock r12 = r12.systemClock
            long r3 = r12.elapsedRealtime()
            kotlin.jvm.internal.Ref$ObjectRef r12 = new kotlin.jvm.internal.Ref$ObjectRef
            r12.<init>()
            com.android.systemui.unfold.DisplaySwitchLatencyTracker r1 = r11.this$0     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge r1 = r1.startOrEndEvent     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$Companion r5 = com.android.systemui.unfold.DisplaySwitchLatencyTracker.Companion     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            r5.getClass()     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            long r5 = com.android.systemui.unfold.DisplaySwitchLatencyTracker.COOL_DOWN_DURATION     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            kotlinx.coroutines.flow.internal.FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1 r1 = kotlinx.coroutines.flow.FlowKt.m3463timeoutHG0u8IE(r1, r5)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$startCoolDown$1$1 r5 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$startCoolDown$1$1     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            r5.<init>()     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            r11.L$0 = r12     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            r11.J$0 = r3     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            r11.label = r2     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            java.lang.Object r11 = r1.collect(r5, r11)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L4d
            if (r11 != r0) goto L90
            return r0
        L4d:
            r2 = r12
            r0 = r3
        L4f:
            com.android.systemui.unfold.DisplaySwitchLatencyTracker r12 = r11.this$0
            com.android.systemui.util.time.SystemClock r12 = r12.systemClock
            long r3 = r12.elapsedRealtime()
            long r8 = r3 - r0
            com.android.systemui.unfold.DisplaySwitchLatencyTracker r5 = r11.this$0
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$DisplaySwitchLatencyEvent r6 = r11.$event
            T r12 = r2.element
            com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r12 = (com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState) r12
            if (r12 != 0) goto L65
            com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r12 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.UNKNOWN
        L65:
            r7 = r12
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$TrackingResult r10 = com.android.systemui.unfold.DisplaySwitchLatencyTracker.TrackingResult.CORRUPTED
            r5.logDisplaySwitchEvent(r6, r7, r8, r10)
            boolean r12 = android.os.Trace.isEnabled()
            if (r12 == 0) goto L8b
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r0 = "cool down finished, lasted "
            r12.<init>(r0)
            r12.append(r8)
            java.lang.String r0 = " ms"
            r12.append(r0)
            java.lang.String r12 = r12.toString()
            r0 = 4096(0x1000, double:2.0237E-320)
            java.lang.String r2 = "DisplaySwitchLatency"
            android.os.Trace.instantForTrack(r0, r2, r12)
        L8b:
            com.android.systemui.unfold.DisplaySwitchLatencyTracker r11 = r11.this$0
            r12 = 0
            r11.isCoolingDown = r12
        L90:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$startCoolDown$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
