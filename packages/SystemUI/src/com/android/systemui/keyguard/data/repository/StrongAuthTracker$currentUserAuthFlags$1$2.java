package com.android.systemui.keyguard.data.repository;

import android.util.Log;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class StrongAuthTracker$currentUserAuthFlags$1$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public StrongAuthTracker$currentUserAuthFlags$1$2(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StrongAuthTracker$currentUserAuthFlags$1$2 strongAuthTracker$currentUserAuthFlags$1$2 = new StrongAuthTracker$currentUserAuthFlags$1$2(continuation);
        strongAuthTracker$currentUserAuthFlags$1$2.L$0 = obj;
        return strongAuthTracker$currentUserAuthFlags$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StrongAuthTracker$currentUserAuthFlags$1$2) create((AuthenticationFlags) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d("BiometricsRepositoryImpl", "currentUser authFlags changed, new value: " + ((AuthenticationFlags) this.L$0));
        return Unit.INSTANCE;
    }
}
