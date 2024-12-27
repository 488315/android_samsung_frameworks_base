package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class IpcSerializer$runSerialized$1<R> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ IpcSerializer this$0;

    public IpcSerializer$runSerialized$1(IpcSerializer ipcSerializer, Continuation continuation) {
        super(continuation);
        this.this$0 = ipcSerializer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.runSerialized(null, this);
    }
}
