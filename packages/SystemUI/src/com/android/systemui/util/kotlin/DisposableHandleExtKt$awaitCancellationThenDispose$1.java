package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class DisposableHandleExtKt$awaitCancellationThenDispose$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    public DisposableHandleExtKt$awaitCancellationThenDispose$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DisposableHandleExtKt.awaitCancellationThenDispose(null, this);
    }
}
