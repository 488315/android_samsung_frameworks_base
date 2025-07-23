package com.android.systemui.authentication.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AuthenticationInteractor$shouldSkipAuthenticationAttempt$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AuthenticationInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationInteractor$shouldSkipAuthenticationAttempt$1(AuthenticationInteractor authenticationInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = authenticationInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        AuthenticationInteractor authenticationInteractor = this.this$0;
        int i = AuthenticationInteractor.$r8$clinit;
        return authenticationInteractor.shouldSkipAuthenticationAttempt(null, false, 0, this);
    }
}
