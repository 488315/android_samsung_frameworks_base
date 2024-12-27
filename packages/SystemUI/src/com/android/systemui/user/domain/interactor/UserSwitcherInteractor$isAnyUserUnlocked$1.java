package com.android.systemui.user.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class UserSwitcherInteractor$isAnyUserUnlocked$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserSwitcherInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherInteractor$isAnyUserUnlocked$1(UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        UserSwitcherInteractor userSwitcherInteractor = this.this$0;
        int i = UserSwitcherInteractor.$r8$clinit;
        return userSwitcherInteractor.isAnyUserUnlocked(this);
    }
}
