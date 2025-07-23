package com.android.systemui.qs;

import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SecQSGradationDrawableController$onViewAttached$1$1$2 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    int label;

    public SecQSGradationDrawableController$onViewAttached$1$1$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float floatValue = ((Number) obj).floatValue();
        SecQSGradationDrawableController$onViewAttached$1$1$2 secQSGradationDrawableController$onViewAttached$1$1$2 = new SecQSGradationDrawableController$onViewAttached$1$1$2((Continuation) obj3);
        secQSGradationDrawableController$onViewAttached$1$1$2.F$0 = floatValue;
        secQSGradationDrawableController$onViewAttached$1$1$2.L$0 = (SecQsUiDisplayModeInteractor.UiDisplayMode) obj2;
        return secQSGradationDrawableController$onViewAttached$1$1$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        if (((SecQsUiDisplayModeInteractor.UiDisplayMode) this.L$0) == SecQsUiDisplayModeInteractor.UiDisplayMode.LARGE) {
            f = 0.0f;
        }
        return new Float(f);
    }
}
