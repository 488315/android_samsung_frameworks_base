package com.android.systemui.communal.data.repository;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class CommunalPrefsRepositoryImpl$isCtaDismissed$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public CommunalPrefsRepositoryImpl$isCtaDismissed$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalPrefsRepositoryImpl$isCtaDismissed$2 communalPrefsRepositoryImpl$isCtaDismissed$2 = new CommunalPrefsRepositoryImpl$isCtaDismissed$2((Continuation) obj3);
        communalPrefsRepositoryImpl$isCtaDismissed$2.L$0 = (UserInfo) obj;
        return communalPrefsRepositoryImpl$isCtaDismissed$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return (UserInfo) this.L$0;
    }
}
