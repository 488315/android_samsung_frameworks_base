package com.android.systemui.user.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;

    public UserSwitcherRepositoryImpl$isEnabled$1$updateState$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return UserSwitcherRepositoryImpl$isEnabled$1.invokeSuspend$updateState(null, null, this);
    }
}
