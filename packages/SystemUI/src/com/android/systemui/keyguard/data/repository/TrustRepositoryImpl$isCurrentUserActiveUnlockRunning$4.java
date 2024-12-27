package com.android.systemui.keyguard.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ TrustRepositoryImpl this$0;

    public TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4(TrustRepositoryImpl trustRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = trustRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4 trustRepositoryImpl$isCurrentUserActiveUnlockRunning$4 = new TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4(this.this$0, continuation);
        trustRepositoryImpl$isCurrentUserActiveUnlockRunning$4.Z$0 = ((Boolean) obj).booleanValue();
        return trustRepositoryImpl$isCurrentUserActiveUnlockRunning$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.logger.isCurrentUserActiveUnlockRunning(this.Z$0);
        return Unit.INSTANCE;
    }
}
