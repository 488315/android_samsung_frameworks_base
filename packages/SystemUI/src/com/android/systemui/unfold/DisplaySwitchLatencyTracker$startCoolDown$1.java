package com.android.systemui.unfold;

import android.os.Trace;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1;

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
    /* JADX WARN: Removed duplicated region for block: B:17:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0071  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Ref$ObjectRef ref$ObjectRef;
        long j;
        DeviceStateRepository.DeviceState deviceState;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long jElapsedRealtime = this.this$0.systemClock.elapsedRealtime();
            final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            try {
                ChannelLimitedFlowMerge channelLimitedFlowMerge = this.this$0.startOrEndEvent;
                DisplaySwitchLatencyTracker.Companion.getClass();
                FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1 flowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1M3482timeoutHG0u8IE = FlowKt.m3482timeoutHG0u8IE(channelLimitedFlowMerge, DisplaySwitchLatencyTracker.COOL_DOWN_DURATION);
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$startCoolDown$1.1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (obj2 instanceof WithPrev) {
                            Object newValue = ((WithPrev) obj2).getNewValue();
                            ref$ObjectRef2.element = newValue instanceof DeviceStateRepository.DeviceState ? (DeviceStateRepository.DeviceState) newValue : 0;
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = ref$ObjectRef2;
                this.J$0 = jElapsedRealtime;
                this.label = 1;
                if (flowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1M3482timeoutHG0u8IE.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } catch (TimeoutCancellationException unused) {
                ref$ObjectRef = ref$ObjectRef2;
                j = jElapsedRealtime;
                long jElapsedRealtime2 = this.this$0.systemClock.elapsedRealtime() - j;
                DisplaySwitchLatencyTracker displaySwitchLatencyTracker = this.this$0;
                DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent = this.$event;
                deviceState = (DeviceStateRepository.DeviceState) ref$ObjectRef.element;
                if (deviceState == null) {
                }
                displaySwitchLatencyTracker.logDisplaySwitchEvent(displaySwitchLatencyEvent, deviceState, jElapsedRealtime2, DisplaySwitchLatencyTracker.TrackingResult.CORRUPTED);
                if (Trace.isEnabled()) {
                }
                this.this$0.isCoolingDown = false;
                return Unit.INSTANCE;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j = this.J$0;
            ref$ObjectRef = (Ref$ObjectRef) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (TimeoutCancellationException unused2) {
                long jElapsedRealtime22 = this.this$0.systemClock.elapsedRealtime() - j;
                DisplaySwitchLatencyTracker displaySwitchLatencyTracker2 = this.this$0;
                DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent2 = this.$event;
                deviceState = (DeviceStateRepository.DeviceState) ref$ObjectRef.element;
                if (deviceState == null) {
                    deviceState = DeviceStateRepository.DeviceState.UNKNOWN;
                }
                displaySwitchLatencyTracker2.logDisplaySwitchEvent(displaySwitchLatencyEvent2, deviceState, jElapsedRealtime22, DisplaySwitchLatencyTracker.TrackingResult.CORRUPTED);
                if (Trace.isEnabled()) {
                    Trace.instantForTrack(4096L, "DisplaySwitchLatency", "cool down finished, lasted " + jElapsedRealtime22 + " ms");
                }
                this.this$0.isCoolingDown = false;
                return Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }
}
