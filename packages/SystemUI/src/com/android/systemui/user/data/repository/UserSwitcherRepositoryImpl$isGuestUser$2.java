package com.android.systemui.user.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class UserSwitcherRepositoryImpl$isGuestUser$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    public UserSwitcherRepositoryImpl$isGuestUser$2(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherRepositoryImpl$isGuestUser$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherRepositoryImpl$isGuestUser$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
        return Boolean.valueOf(userSwitcherRepositoryImpl.userManager.isGuestUser(((UserRepositoryImpl) userSwitcherRepositoryImpl.userRepository).getSelectedUserInfo().id));
    }
}
