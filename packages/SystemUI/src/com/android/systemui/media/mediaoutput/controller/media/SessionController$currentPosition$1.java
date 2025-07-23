package com.android.systemui.media.mediaoutput.controller.media;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SessionController$currentPosition$1 extends SuspendLambda implements Function3 {
    /* synthetic */ long J$0;
    /* synthetic */ long J$1;
    int label;

    public SessionController$currentPosition$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        long longValue = ((Number) obj).longValue();
        long longValue2 = ((Number) obj2).longValue();
        SessionController$currentPosition$1 sessionController$currentPosition$1 = new SessionController$currentPosition$1((Continuation) obj3);
        sessionController$currentPosition$1.J$0 = longValue;
        sessionController$currentPosition$1.J$1 = longValue2;
        return sessionController$currentPosition$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        long j = this.J$0;
        long j2 = this.J$1;
        Long l = new Long(j);
        if (l.longValue() <= 0) {
            l = null;
        }
        return new Float(l != null ? j2 / l.longValue() : 0.0f);
    }
}
