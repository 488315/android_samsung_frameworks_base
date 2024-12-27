package com.android.systemui.keyguard.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class TransitionInteractor$maybeHandleInsecurePowerGesture$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ TransitionInteractor this$0;

    public TransitionInteractor$maybeHandleInsecurePowerGesture$1(TransitionInteractor transitionInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = transitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.maybeHandleInsecurePowerGesture(this);
    }
}
