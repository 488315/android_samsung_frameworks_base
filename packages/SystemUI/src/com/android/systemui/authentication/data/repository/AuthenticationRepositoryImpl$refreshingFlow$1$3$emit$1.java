package com.android.systemui.authentication.data.repository;

import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AuthenticationRepositoryImpl$refreshingFlow$1.AnonymousClass3 this$0;

    public AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1(AuthenticationRepositoryImpl$refreshingFlow$1.AnonymousClass3 anonymousClass3, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(0, this);
    }
}
