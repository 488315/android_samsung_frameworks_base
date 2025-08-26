package com.android.systemui.statusbar.notification.promoted.ui.viewmodel;

import com.android.systemui.util.time.SystemClock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class AODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ SystemClock $systemClock;
    long J$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1(SystemClock systemClock, Continuation continuation) {
        super(3, continuation);
        this.$systemClock = systemClock;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1 aODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1 = new AODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1(this.$systemClock, (Continuation) obj3);
        aODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1.L$0 = (FlowCollector) obj;
        aODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1.L$1 = (Duration) obj2;
        return aODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x008c, code lost:
    
        if (r11.emit(r1, r10) == r0) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Duration duration;
        long j;
        FlowCollector flowCollector2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            duration = (Duration) this.L$1;
            Duration.Companion companion = Duration.Companion;
            long duration2 = DurationKt.toDuration(this.$systemClock.currentTimeMillis(), DurationUnit.MILLISECONDS);
            if (duration == null || Duration.m3455compareToLRDsOJo(duration.rawValue, duration2) <= 0) {
                Boolean bool = Boolean.FALSE;
                this.L$0 = null;
                this.label = 3;
            } else {
                Boolean bool2 = Boolean.TRUE;
                this.L$0 = flowCollector;
                this.L$1 = duration;
                this.J$0 = duration2;
                this.label = 1;
                if (flowCollector.emit(bool2, this) != coroutineSingletons) {
                    j = duration2;
                }
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            j = this.J$0;
            duration = (Duration) this.L$1;
            FlowCollector flowCollector3 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector = flowCollector3;
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector = flowCollector2;
            Boolean bool3 = Boolean.FALSE;
            this.L$0 = null;
            this.label = 3;
        }
        long jM3461plusLRDsOJo = Duration.m3461plusLRDsOJo(duration.rawValue, Duration.m3467unaryMinusUwyO8pc(j));
        this.L$0 = flowCollector;
        this.L$1 = null;
        this.label = 2;
        if (DelayKt.m3469delayVtjQ1oo(jM3461plusLRDsOJo, this) != coroutineSingletons) {
            flowCollector2 = flowCollector;
            flowCollector = flowCollector2;
            Boolean bool32 = Boolean.FALSE;
            this.L$0 = null;
            this.label = 3;
        }
        return coroutineSingletons;
    }
}
