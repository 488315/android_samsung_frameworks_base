package com.android.systemui.bouncer.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

final class AlternateBouncerInteractor$canShowAlternateBouncer$1$3$1 extends SuspendLambda implements Function5 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;

    public AlternateBouncerInteractor$canShowAlternateBouncer$1$3$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
        AlternateBouncerInteractor$canShowAlternateBouncer$1$3$1 alternateBouncerInteractor$canShowAlternateBouncer$1$3$1 = new AlternateBouncerInteractor$canShowAlternateBouncer$1$3$1((Continuation) obj5);
        alternateBouncerInteractor$canShowAlternateBouncer$1$3$1.Z$0 = booleanValue;
        alternateBouncerInteractor$canShowAlternateBouncer$1$3$1.Z$1 = booleanValue2;
        alternateBouncerInteractor$canShowAlternateBouncer$1$3$1.Z$2 = booleanValue3;
        alternateBouncerInteractor$canShowAlternateBouncer$1$3$1.Z$3 = booleanValue4;
        return alternateBouncerInteractor$canShowAlternateBouncer$1$3$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((!this.Z$0 || this.Z$1 || this.Z$2 || this.Z$3) ? false : true);
    }
}
