package com.android.systemui.authentication.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class AuthenticationRepositoryImpl$getPinLength$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AuthenticationRepositoryImpl this$0;

    public AuthenticationRepositoryImpl$getPinLength$2(AuthenticationRepositoryImpl authenticationRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authenticationRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AuthenticationRepositoryImpl$getPinLength$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationRepositoryImpl$getPinLength$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AuthenticationRepositoryImpl authenticationRepositoryImpl = this.this$0;
        return new Integer(authenticationRepositoryImpl.lockPatternUtils.getPinLength(authenticationRepositoryImpl.getSelectedUserId()));
    }
}
