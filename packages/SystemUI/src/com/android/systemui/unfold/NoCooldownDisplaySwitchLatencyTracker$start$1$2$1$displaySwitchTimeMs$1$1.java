package com.android.systemui.unfold;

import android.os.Trace;
import com.android.app.tracing.TraceUtils;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NoCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $toFoldableDeviceState;
    int I$0;
    long J$0;
    Object L$0;
    int label;
    final /* synthetic */ NoCooldownDisplaySwitchLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1(NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noCooldownDisplaySwitchLatencyTracker;
        this.$toFoldableDeviceState = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NoCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1(this.this$0, this.$toFoldableDeviceState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoCooldownDisplaySwitchLatencyTracker$start$1$2$1$displaySwitchTimeMs$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        long j;
        String str;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            NoCooldownDisplaySwitchLatencyTracker noCooldownDisplaySwitchLatencyTracker = this.this$0;
            int i3 = this.$toFoldableDeviceState;
            int i4 = TraceUtils.$r8$clinit;
            int nextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "displaySwitch", nextInt);
            try {
                this.L$0 = "DisplaySwitchLatency";
                this.J$0 = 4096L;
                this.I$0 = nextInt;
                this.label = 1;
                if (NoCooldownDisplaySwitchLatencyTracker.access$waitForDisplaySwitch(noCooldownDisplaySwitchLatencyTracker, i3, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i = nextInt;
                j = 4096;
                str = "DisplaySwitchLatency";
            } catch (Throwable th) {
                th = th;
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
            j = this.J$0;
            str = (String) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th2) {
                th = th2;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        }
        Unit unit = Unit.INSTANCE;
        Trace.asyncTraceForTrackEnd(j, str, i);
        return Unit.INSTANCE;
    }
}
