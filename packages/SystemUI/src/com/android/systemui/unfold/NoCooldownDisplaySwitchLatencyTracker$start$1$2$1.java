package com.android.systemui.unfold;

import android.os.Trace;
import android.util.Log;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.time.SystemClock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.flow.FlowCollector;

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

    /* JADX WARN: Code restructure failed: missing block: B:61:0x017f, code lost:
    
        if (r14.emit(r2, r41) == r1) goto L62;
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0151  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEventCopy$default;
        SystemClock systemClock;
        int i;
        int i2;
        long j;
        long j2;
        NoCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1 noCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = this.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent = new DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent(0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8388607, null);
            NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker = this.this$0;
            DeviceStateRepository.DeviceState deviceState = (DeviceStateRepository.DeviceState) this.$foldableDeviceState.getNewValue();
            int i4 = NoCooldownDisplaySwitchLatencyTracker.$r8$clinit;
            noCooldownDisplaySwitchLatencyTracker.getClass();
            int[] iArr = NoCooldownDisplaySwitchLatencyTracker.WhenMappings.$EnumSwitchMapping$0;
            int i5 = iArr[deviceState.ordinal()];
            int i6 = 4;
            int i7 = i5 != 1 ? i5 != 2 ? i5 != 3 ? i5 != 4 ? 0 : 4 : 3 : 2 : 1;
            NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker2 = this.this$0;
            DeviceStateRepository.DeviceState previousValue = this.$foldableDeviceState.getPreviousValue();
            noCooldownDisplaySwitchLatencyTracker2.getClass();
            int i8 = iArr[previousValue.ordinal()];
            if (i8 == 1) {
                i6 = 1;
            } else if (i8 == 2) {
                i6 = 2;
            } else if (i8 == 3) {
                i6 = 3;
            } else if (i8 != 4) {
                i6 = 0;
            }
            if (Trace.isEnabled()) {
                Trace.instantForTrack(4096L, "DisplaySwitchLatency", "fromFoldableDeviceState=" + i6);
            }
            displaySwitchLatencyEventCopy$default = DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent, 0, i6, 0, 0, 0, 0, 8388605);
            NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker3 = this.this$0;
            systemClock = noCooldownDisplaySwitchLatencyTracker3.systemClock;
            long jCurrentTimeMillis = systemClock.currentTimeMillis();
            try {
                j2 = NoCooldownDisplaySwitchLatencyTracker.SCREEN_EVENT_TIMEOUT;
                noCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1 = new NoCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1(noCooldownDisplaySwitchLatencyTracker3, i7, null);
                this.L$0 = flowCollector;
                this.L$1 = displaySwitchLatencyEventCopy$default;
                this.L$2 = systemClock;
                this.I$0 = i7;
                this.J$0 = jCurrentTimeMillis;
                i = 1;
                try {
                    this.label = 1;
                } catch (TimeoutCancellationException unused) {
                    i2 = i7;
                    j = jCurrentTimeMillis;
                    Log.e("DisplaySwitchLatency", "Wait for display switch timed out");
                    DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent2 = displaySwitchLatencyEventCopy$default;
                    long jCurrentTimeMillis2 = systemClock.currentTimeMillis() - j;
                    NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker4 = this.this$0;
                    int i9 = (int) jCurrentTimeMillis2;
                    int i10 = NoCooldownDisplaySwitchLatencyTracker.$r8$clinit;
                    if (noCooldownDisplaySwitchLatencyTracker4.isAsleepDueToFold$1()) {
                    }
                    if (Trace.isEnabled()) {
                    }
                    DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEventCopy$default2 = DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent2, i9, 0, i2, i, 0, 0, 8388222);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.L$2 = null;
                    this.label = 2;
                }
            } catch (TimeoutCancellationException unused2) {
                i = 1;
            }
            if (TimeoutKt.withTimeout(j2, noCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1, this) != coroutineSingletons) {
                i2 = i7;
                j = jCurrentTimeMillis;
                DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent22 = displaySwitchLatencyEventCopy$default;
                long jCurrentTimeMillis22 = systemClock.currentTimeMillis() - j;
                NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker42 = this.this$0;
                int i92 = (int) jCurrentTimeMillis22;
                int i102 = NoCooldownDisplaySwitchLatencyTracker.$r8$clinit;
                if (noCooldownDisplaySwitchLatencyTracker42.isAsleepDueToFold$1()) {
                }
                if (Trace.isEnabled()) {
                }
                DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEventCopy$default22 = DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent22, i92, 0, i2, i, 0, 0, 8388222);
                this.L$0 = null;
                this.L$1 = null;
                this.L$2 = null;
                this.label = 2;
            }
            return coroutineSingletons;
        }
        if (i3 != 1) {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        j = this.J$0;
        i2 = this.I$0;
        systemClock = (SystemClock) this.L$2;
        displaySwitchLatencyEventCopy$default = (DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent) this.L$1;
        flowCollector = (FlowCollector) this.L$0;
        try {
            ResultKt.throwOnFailure(obj);
            i = 1;
        } catch (TimeoutCancellationException unused3) {
            i = 1;
            Log.e("DisplaySwitchLatency", "Wait for display switch timed out");
            DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent222 = displaySwitchLatencyEventCopy$default;
            long jCurrentTimeMillis222 = systemClock.currentTimeMillis() - j;
            NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker422 = this.this$0;
            int i922 = (int) jCurrentTimeMillis222;
            int i1022 = NoCooldownDisplaySwitchLatencyTracker.$r8$clinit;
            if (noCooldownDisplaySwitchLatencyTracker422.isAsleepDueToFold$1()) {
            }
            if (Trace.isEnabled()) {
            }
            DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEventCopy$default222 = DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent222, i922, 0, i2, i, 0, 0, 8388222);
            this.L$0 = null;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 2;
        }
        DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent2222 = displaySwitchLatencyEventCopy$default;
        long jCurrentTimeMillis2222 = systemClock.currentTimeMillis() - j;
        NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker4222 = this.this$0;
        int i9222 = (int) jCurrentTimeMillis2222;
        int i10222 = NoCooldownDisplaySwitchLatencyTracker.$r8$clinit;
        int i11 = (noCooldownDisplaySwitchLatencyTracker4222.isAsleepDueToFold$1() || !noCooldownDisplaySwitchLatencyTracker4222.isAodEnabled$1()) ? (noCooldownDisplaySwitchLatencyTracker4222.isAsleepDueToFold$1() || noCooldownDisplaySwitchLatencyTracker4222.isAodEnabled$1()) ? 0 : 9 : i;
        if (Trace.isEnabled()) {
            Trace.instantForTrack(4096L, "DisplaySwitchLatency", ListImplementation$$ExternalSyntheticOutline0.m(i2, i11, "toFoldableDeviceState=", ", toState="));
        }
        DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEventCopy$default2222 = DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent2222, i9222, 0, i2, i11, 0, 0, 8388222);
        this.L$0 = null;
        this.L$1 = null;
        this.L$2 = null;
        this.label = 2;
    }
}
