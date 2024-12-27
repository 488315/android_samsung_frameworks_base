package com.android.systemui.user.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class UserSwitcherInteractor$toRecord$2 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserSwitcherInteractor this$0;

    public UserSwitcherInteractor$toRecord$2(UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return UserSwitcherInteractor.access$toRecord(this.this$0, null, 0, false, this);
    }
}
