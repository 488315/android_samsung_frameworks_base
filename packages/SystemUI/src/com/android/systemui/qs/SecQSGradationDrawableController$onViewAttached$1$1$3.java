package com.android.systemui.qs;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class SecQSGradationDrawableController$onViewAttached$1$1$3 extends SuspendLambda implements Function2 {
    /* synthetic */ float F$0;
    int label;
    final /* synthetic */ SecQSGradationDrawableController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecQSGradationDrawableController$onViewAttached$1$1$3(SecQSGradationDrawableController secQSGradationDrawableController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = secQSGradationDrawableController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecQSGradationDrawableController$onViewAttached$1$1$3 secQSGradationDrawableController$onViewAttached$1$1$3 = new SecQSGradationDrawableController$onViewAttached$1$1$3(this.this$0, continuation);
        secQSGradationDrawableController$onViewAttached$1$1$3.F$0 = ((Number) obj).floatValue();
        return secQSGradationDrawableController$onViewAttached$1$1$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecQSGradationDrawableController$onViewAttached$1$1$3) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.view.setAlpha(this.F$0);
        return Unit.INSTANCE;
    }
}
