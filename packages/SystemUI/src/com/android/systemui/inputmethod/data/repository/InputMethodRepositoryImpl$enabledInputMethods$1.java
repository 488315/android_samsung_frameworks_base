package com.android.systemui.inputmethod.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class InputMethodRepositoryImpl$enabledInputMethods$1 extends ContinuationImpl {
    Object L$0;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ InputMethodRepositoryImpl this$0;

    public InputMethodRepositoryImpl$enabledInputMethods$1(InputMethodRepositoryImpl inputMethodRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = inputMethodRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.enabledInputMethods(0, this, false);
    }
}
