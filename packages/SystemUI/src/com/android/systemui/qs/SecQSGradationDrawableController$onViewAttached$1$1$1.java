package com.android.systemui.qs;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
final class SecQSGradationDrawableController$onViewAttached$1$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ int I$0;
    int label;

    public SecQSGradationDrawableController$onViewAttached$1$1$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float fFloatValue = ((Number) obj).floatValue();
        float fFloatValue2 = ((Number) obj2).floatValue();
        int iIntValue = ((Number) obj3).intValue();
        SecQSGradationDrawableController$onViewAttached$1$1$1 secQSGradationDrawableController$onViewAttached$1$1$1 = new SecQSGradationDrawableController$onViewAttached$1$1$1((Continuation) obj4);
        secQSGradationDrawableController$onViewAttached$1$1$1.F$0 = fFloatValue;
        secQSGradationDrawableController$onViewAttached$1$1$1.F$1 = fFloatValue2;
        secQSGradationDrawableController$onViewAttached$1$1$1.I$0 = iIntValue;
        return secQSGradationDrawableController$onViewAttached$1$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        float f2 = this.F$1;
        if (this.I$0 == 1) {
            f = f2;
        }
        return new Float(f);
    }
}
