package com.android.systemui.statusbar.policy.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

final class UserSetupRepositoryImpl$isUserSetUp$3 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSetupRepositoryImpl this$0;

    public UserSetupRepositoryImpl$isUserSetUp$3(UserSetupRepositoryImpl userSetupRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSetupRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSetupRepositoryImpl$isUserSetUp$3(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSetupRepositoryImpl$isUserSetUp$3) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserSetupRepositoryImpl userSetupRepositoryImpl = this.this$0;
            this.label = 1;
            userSetupRepositoryImpl.getClass();
            obj = BuildersKt.withContext(userSetupRepositoryImpl.bgDispatcher, new UserSetupRepositoryImpl$fetchUserSetupState$2(userSetupRepositoryImpl, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
