package com.android.systemui.communal.ui.compose.extensions;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class PointerInputScopeExtKt$consumeUntilUp$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;

    public PointerInputScopeExtKt$consumeUntilUp$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return PointerInputScopeExtKt.access$consumeUntilUp(null, null, this);
    }
}
