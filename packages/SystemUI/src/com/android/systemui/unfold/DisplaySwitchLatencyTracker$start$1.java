package com.android.systemui.unfold;

import android.os.Trace;
import com.android.app.tracing.TraceUtils;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.time.SystemClock;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DisplaySwitchLatencyTracker$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DisplaySwitchLatencyTracker this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ DisplaySwitchLatencyTracker this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent $event;
            final /* synthetic */ DeviceStateRepository.DeviceState $newState;
            final /* synthetic */ DeviceStateRepository.DeviceState $previousState;
            int I$0;
            long J$0;
            long J$1;
            Object L$0;
            Object L$1;
            int label;
            final /* synthetic */ DisplaySwitchLatencyTracker this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, DeviceStateRepository.DeviceState deviceState, DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent, DeviceStateRepository.DeviceState deviceState2, Continuation continuation) {
                super(2, continuation);
                this.this$0 = displaySwitchLatencyTracker;
                this.$previousState = deviceState;
                this.$event = displaySwitchLatencyEvent;
                this.$newState = deviceState2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, this.$previousState, this.$event, this.$newState, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                long currentTimeMillis;
                Throwable th;
                int i;
                long j;
                String str;
                SystemClock systemClock;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = this.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    DisplaySwitchLatencyTracker displaySwitchLatencyTracker = this.this$0;
                    SystemClock systemClock2 = displaySwitchLatencyTracker.systemClock;
                    DeviceStateRepository.DeviceState deviceState = this.$newState;
                    currentTimeMillis = systemClock2.currentTimeMillis();
                    int i3 = TraceUtils.$r8$clinit;
                    int nextInt = ThreadLocalRandom.current().nextInt();
                    Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "displaySwitch", nextInt);
                    try {
                        int statsInt = DisplaySwitchLatencyTracker.toStatsInt(deviceState);
                        this.L$0 = systemClock2;
                        this.L$1 = "DisplaySwitchLatency";
                        this.J$0 = currentTimeMillis;
                        this.J$1 = 4096L;
                        this.I$0 = nextInt;
                        this.label = 1;
                        if (DisplaySwitchLatencyTracker.access$waitForDisplaySwitch(displaySwitchLatencyTracker, statsInt, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        systemClock = systemClock2;
                        i = nextInt;
                        j = 4096;
                        str = "DisplaySwitchLatency";
                    } catch (Throwable th2) {
                        th = th2;
                        i = nextInt;
                        j = 4096;
                        str = "DisplaySwitchLatency";
                        Trace.asyncTraceForTrackEnd(j, str, i);
                        throw th;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    i = this.I$0;
                    j = this.J$1;
                    currentTimeMillis = this.J$0;
                    str = (String) this.L$1;
                    systemClock = (SystemClock) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                    } catch (Throwable th3) {
                        th = th3;
                        Trace.asyncTraceForTrackEnd(j, str, i);
                        throw th;
                    }
                }
                Unit unit = Unit.INSTANCE;
                Trace.asyncTraceForTrackEnd(j, str, i);
                long currentTimeMillis2 = systemClock.currentTimeMillis() - currentTimeMillis;
                if (this.$previousState == DeviceStateRepository.DeviceState.FOLDED) {
                    this.this$0.latencyTracker.onActionEnd(13);
                }
                DisplaySwitchLatencyTracker displaySwitchLatencyTracker2 = this.this$0;
                DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent = this.$event;
                DeviceStateRepository.DeviceState deviceState2 = this.$newState;
                DisplaySwitchLatencyTracker.Companion companion = DisplaySwitchLatencyTracker.Companion;
                displaySwitchLatencyTracker2.logDisplaySwitchEvent(displaySwitchLatencyEvent, deviceState2, currentTimeMillis2, DisplaySwitchLatencyTracker.TrackingResult.SUCCESS);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, Continuation continuation) {
            super(2, continuation);
            this.this$0 = displaySwitchLatencyTracker;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((WithPrev) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0136  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x010a  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x011f  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r42) {
            /*
                Method dump skipped, instructions count: 348
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplaySwitchLatencyTracker$start$1(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displaySwitchLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DisplaySwitchLatencyTracker$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplaySwitchLatencyTracker$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DisplaySwitchLatencyTracker displaySwitchLatencyTracker = this.this$0;
            DisplaySwitchLatencyTracker$special$$inlined$filter$1 displaySwitchLatencyTracker$special$$inlined$filter$1 = displaySwitchLatencyTracker.displaySwitchStarted;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(displaySwitchLatencyTracker, null);
            this.label = 1;
            if (FlowKt.collectLatest(displaySwitchLatencyTracker$special$$inlined$filter$1, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
