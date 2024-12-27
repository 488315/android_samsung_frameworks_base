package com.android.systemui.screenshot.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ProfileTypeRepositoryImpl$getProfileType$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ProfileTypeRepositoryImpl this$0;

    public ProfileTypeRepositoryImpl$getProfileType$1(ProfileTypeRepositoryImpl profileTypeRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = profileTypeRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getProfileType(0, this);
    }
}
