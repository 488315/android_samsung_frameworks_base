package com.android.systemui.user.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class UserSwitcherInteractor$canSwitchUsers$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserSwitcherInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherInteractor$canSwitchUsers$1(UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        UserSwitcherInteractor userSwitcherInteractor = this.this$0;
        int i = UserSwitcherInteractor.$r8$clinit;
        return userSwitcherInteractor.canSwitchUsers(0, this, false);
    }
}
