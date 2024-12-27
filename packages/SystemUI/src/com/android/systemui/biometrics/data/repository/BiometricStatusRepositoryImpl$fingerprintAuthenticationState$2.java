package com.android.systemui.biometrics.data.repository;

import android.util.Log;
import com.android.systemui.biometrics.shared.model.AuthenticationState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class BiometricStatusRepositoryImpl$fingerprintAuthenticationState$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public BiometricStatusRepositoryImpl$fingerprintAuthenticationState$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BiometricStatusRepositoryImpl$fingerprintAuthenticationState$2 biometricStatusRepositoryImpl$fingerprintAuthenticationState$2 = new BiometricStatusRepositoryImpl$fingerprintAuthenticationState$2(continuation);
        biometricStatusRepositoryImpl$fingerprintAuthenticationState$2.L$0 = obj;
        return biometricStatusRepositoryImpl$fingerprintAuthenticationState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricStatusRepositoryImpl$fingerprintAuthenticationState$2) create((AuthenticationState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d("BiometricStatusRepositoryImpl", "fingerprintAuthenticationState updated: " + ((AuthenticationState) this.L$0));
        return Unit.INSTANCE;
    }
}
