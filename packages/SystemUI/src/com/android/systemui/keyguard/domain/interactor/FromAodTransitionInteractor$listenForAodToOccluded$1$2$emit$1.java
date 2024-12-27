package com.android.systemui.keyguard.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FromAodTransitionInteractor$listenForAodToOccluded$1$2 this$0;

    public FromAodTransitionInteractor$listenForAodToOccluded$1$2$emit$1(FromAodTransitionInteractor$listenForAodToOccluded$1$2 fromAodTransitionInteractor$listenForAodToOccluded$1$2, Continuation continuation) {
        super(continuation);
        this.this$0 = fromAodTransitionInteractor$listenForAodToOccluded$1$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(false, (Continuation) this);
    }
}
