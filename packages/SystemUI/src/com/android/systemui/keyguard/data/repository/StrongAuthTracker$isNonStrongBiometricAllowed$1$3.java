package com.android.systemui.keyguard.data.repository;

import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class StrongAuthTracker$isNonStrongBiometricAllowed$1$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;

    public StrongAuthTracker$isNonStrongBiometricAllowed$1$3(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StrongAuthTracker$isNonStrongBiometricAllowed$1$3 strongAuthTracker$isNonStrongBiometricAllowed$1$3 = new StrongAuthTracker$isNonStrongBiometricAllowed$1$3(continuation);
        strongAuthTracker$isNonStrongBiometricAllowed$1$3.Z$0 = ((Boolean) obj).booleanValue();
        return strongAuthTracker$isNonStrongBiometricAllowed$1$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((StrongAuthTracker$isNonStrongBiometricAllowed$1$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isNonStrongBiometricAllowed changed for current user: ", "BiometricsRepositoryImpl", this.Z$0);
        return Unit.INSTANCE;
    }
}
