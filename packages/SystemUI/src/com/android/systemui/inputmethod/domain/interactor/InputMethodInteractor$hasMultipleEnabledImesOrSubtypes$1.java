package com.android.systemui.inputmethod.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ InputMethodInteractor this$0;

    public InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1(InputMethodInteractor inputMethodInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = inputMethodInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.hasMultipleEnabledImesOrSubtypes(0, this);
    }
}
