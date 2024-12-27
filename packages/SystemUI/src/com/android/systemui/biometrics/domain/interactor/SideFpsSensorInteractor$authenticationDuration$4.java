package com.android.systemui.biometrics.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class SideFpsSensorInteractor$authenticationDuration$4 extends SuspendLambda implements Function2 {
    /* synthetic */ long J$0;
    int label;
    final /* synthetic */ SideFpsSensorInteractor this$0;

    public SideFpsSensorInteractor$authenticationDuration$4(SideFpsSensorInteractor sideFpsSensorInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sideFpsSensorInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SideFpsSensorInteractor$authenticationDuration$4 sideFpsSensorInteractor$authenticationDuration$4 = new SideFpsSensorInteractor$authenticationDuration$4(this.this$0, continuation);
        sideFpsSensorInteractor$authenticationDuration$4.J$0 = ((Number) obj).longValue();
        return sideFpsSensorInteractor$authenticationDuration$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SideFpsSensorInteractor$authenticationDuration$4) create(Long.valueOf(((Number) obj).longValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.logger.authDurationChanged(this.J$0);
        return Unit.INSTANCE;
    }
}
