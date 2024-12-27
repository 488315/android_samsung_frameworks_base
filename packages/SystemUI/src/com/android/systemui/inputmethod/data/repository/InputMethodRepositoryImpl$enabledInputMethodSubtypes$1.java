package com.android.systemui.inputmethod.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class InputMethodRepositoryImpl$enabledInputMethodSubtypes$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ InputMethodRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InputMethodRepositoryImpl$enabledInputMethodSubtypes$1(InputMethodRepositoryImpl inputMethodRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = inputMethodRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.enabledInputMethodSubtypes(null, false, this);
    }
}
