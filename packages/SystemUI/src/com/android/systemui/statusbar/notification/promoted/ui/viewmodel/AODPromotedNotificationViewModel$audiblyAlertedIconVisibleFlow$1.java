package com.android.systemui.statusbar.notification.promoted.ui.viewmodel;

import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x008c, code lost:
    
        if (r11.emit(r1, r10) == r0) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 0
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L34
            if (r1 == r5) goto L25
            if (r1 == r4) goto L1d
            if (r1 != r3) goto L15
            kotlin.ResultKt.throwOnFailure(r11)
            goto L8f
        L15:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L1d:
            java.lang.Object r1 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r11)
            goto L81
        L25:
            long r5 = r10.J$0
            java.lang.Object r1 = r10.L$1
            kotlin.time.Duration r1 = (kotlin.time.Duration) r1
            java.lang.Object r7 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            kotlin.ResultKt.throwOnFailure(r11)
            r11 = r7
            goto L69
        L34:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11
            java.lang.Object r1 = r10.L$1
            kotlin.time.Duration r1 = (kotlin.time.Duration) r1
            kotlin.time.Duration$Companion r6 = kotlin.time.Duration.Companion
            com.android.systemui.util.time.SystemClock r6 = r10.$systemClock
            long r6 = r6.currentTimeMillis()
            kotlin.time.DurationUnit r8 = kotlin.time.DurationUnit.MILLISECONDS
            long r6 = kotlin.time.DurationKt.toDuration(r6, r8)
            if (r1 == 0) goto L82
            long r8 = r1.rawValue
            int r8 = kotlin.time.Duration.m3435compareToLRDsOJo(r8, r6)
            if (r8 <= 0) goto L82
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            r10.L$0 = r11
            r10.L$1 = r1
            r10.J$0 = r6
            r10.label = r5
            java.lang.Object r5 = r11.emit(r8, r10)
            if (r5 != r0) goto L68
            goto L8e
        L68:
            r5 = r6
        L69:
            long r7 = r1.rawValue
            long r5 = kotlin.time.Duration.m3447unaryMinusUwyO8pc(r5)
            long r5 = kotlin.time.Duration.m3441plusLRDsOJo(r7, r5)
            r10.L$0 = r11
            r10.L$1 = r2
            r10.label = r4
            java.lang.Object r1 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r5, r10)
            if (r1 != r0) goto L80
            goto L8e
        L80:
            r1 = r11
        L81:
            r11 = r1
        L82:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r10.L$0 = r2
            r10.label = r3
            java.lang.Object r10 = r11.emit(r1, r10)
            if (r10 != r0) goto L8f
        L8e:
            return r0
        L8f:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
