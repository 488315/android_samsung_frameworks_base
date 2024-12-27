package com.android.systemui.authentication.data.repository;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class AuthenticationRepositoryImpl$authenticationMethod$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public AuthenticationRepositoryImpl$authenticationMethod$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Boolean) obj2).booleanValue();
        AuthenticationRepositoryImpl$authenticationMethod$1 authenticationRepositoryImpl$authenticationMethod$1 = new AuthenticationRepositoryImpl$authenticationMethod$1((Continuation) obj3);
        authenticationRepositoryImpl$authenticationMethod$1.L$0 = (UserInfo) obj;
        return authenticationRepositoryImpl$authenticationMethod$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Integer(((UserInfo) this.L$0).id);
    }
}
