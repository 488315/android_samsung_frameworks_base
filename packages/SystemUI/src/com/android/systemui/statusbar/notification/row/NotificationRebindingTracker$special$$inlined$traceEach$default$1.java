package com.android.systemui.statusbar.notification.row;

import com.android.app.tracing.TraceStateLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationRebindingTracker$special$$inlined$traceEach$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ TraceStateLogger $stateLogger;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationRebindingTracker$special$$inlined$traceEach$default$1(TraceStateLogger traceStateLogger, Continuation continuation) {
        super(2, continuation);
        this.$stateLogger = traceStateLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationRebindingTracker$special$$inlined$traceEach$default$1 notificationRebindingTracker$special$$inlined$traceEach$default$1 = new NotificationRebindingTracker$special$$inlined$traceEach$default$1(this.$stateLogger, continuation);
        notificationRebindingTracker$special$$inlined$traceEach$default$1.L$0 = obj;
        return notificationRebindingTracker$special$$inlined$traceEach$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationRebindingTracker$special$$inlined$traceEach$default$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$stateLogger.log(String.valueOf(this.L$0));
        return Unit.INSTANCE;
    }
}
