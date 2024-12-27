package com.android.systemui.keyguard.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class KeyguardTransitionRepositoryImpl$startTransition$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardTransitionRepositoryImpl this$0;

    public KeyguardTransitionRepositoryImpl$startTransition$1(KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardTransitionRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.startTransition(null, this);
    }
}
