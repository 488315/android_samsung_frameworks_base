package com.android.systemui.bouncer.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
final class PrimaryBouncerInteractor$bouncerExpansion$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ boolean Z$0;
    int label;

    public PrimaryBouncerInteractor$bouncerExpansion$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float fFloatValue = ((Number) obj).floatValue();
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        PrimaryBouncerInteractor$bouncerExpansion$1 primaryBouncerInteractor$bouncerExpansion$1 = new PrimaryBouncerInteractor$bouncerExpansion$1((Continuation) obj3);
        primaryBouncerInteractor$bouncerExpansion$1.F$0 = fFloatValue;
        primaryBouncerInteractor$bouncerExpansion$1.Z$0 = zBooleanValue;
        return primaryBouncerInteractor$bouncerExpansion$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Float(this.Z$0 ? 1.0f - this.F$0 : 0.0f);
    }
}
