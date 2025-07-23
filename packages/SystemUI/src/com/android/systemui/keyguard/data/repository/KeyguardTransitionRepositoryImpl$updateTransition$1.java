package com.android.systemui.keyguard.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardTransitionRepositoryImpl$updateTransition$1 extends ContinuationImpl {
    float F$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardTransitionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionRepositoryImpl$updateTransition$1(KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardTransitionRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.updateTransition(null, 0.0f, null, this);
    }
}
