package com.android.systemui.keyguard.domain.interactor.scenetransition;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ LockscreenSceneTransitionInteractor this$0;

    public LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1(LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = lockscreenSceneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.finishReversedTransitionTo(null, this);
    }
}
