package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class TransitionInteractor$listenForTransitionToCamera$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ TransitionInteractor$listenForTransitionToCamera$1.AnonymousClass1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransitionInteractor$listenForTransitionToCamera$1$1$emit$1(TransitionInteractor$listenForTransitionToCamera$1.AnonymousClass1 anonymousClass1, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit$1(this);
    }
}
