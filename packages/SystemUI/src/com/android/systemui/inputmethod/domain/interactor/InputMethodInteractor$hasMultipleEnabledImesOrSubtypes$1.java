package com.android.systemui.inputmethod.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ InputMethodInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
