package com.android.systemui.shade.domain.interactor;

import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class SecNotificationShadeWindowStateInteractor$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    /* synthetic */ boolean Z$0;
    int label;

    public SecNotificationShadeWindowStateInteractor$1$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        int intValue2 = ((Number) obj3).intValue();
        SecNotificationShadeWindowStateInteractor$1$1 secNotificationShadeWindowStateInteractor$1$1 = new SecNotificationShadeWindowStateInteractor$1$1((Continuation) obj4);
        secNotificationShadeWindowStateInteractor$1$1.Z$0 = booleanValue;
        secNotificationShadeWindowStateInteractor$1$1.I$0 = intValue;
        secNotificationShadeWindowStateInteractor$1$1.I$1 = intValue2;
        return secNotificationShadeWindowStateInteractor$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Triple(Boolean.valueOf(this.Z$0), new Integer(this.I$0), new Integer(this.I$1));
    }
}
