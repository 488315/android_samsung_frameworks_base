package com.android.systemui.biometrics.data.repository;

import android.util.Log;
import com.android.systemui.biometrics.shared.model.AuthenticationState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class BiometricStatusRepositoryImpl$fingerprintRunningState$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public BiometricStatusRepositoryImpl$fingerprintRunningState$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BiometricStatusRepositoryImpl$fingerprintRunningState$2 biometricStatusRepositoryImpl$fingerprintRunningState$2 = new BiometricStatusRepositoryImpl$fingerprintRunningState$2(continuation);
        biometricStatusRepositoryImpl$fingerprintRunningState$2.L$0 = obj;
        return biometricStatusRepositoryImpl$fingerprintRunningState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricStatusRepositoryImpl$fingerprintRunningState$2) create((AuthenticationState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d("BiometricStatusRepositoryImpl", "fingerprintRunningState updated: " + ((AuthenticationState) this.L$0));
        return Unit.INSTANCE;
    }
}
