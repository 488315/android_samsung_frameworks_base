package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ParallelKt$mapValuesParallel$1<K, A, B> extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;

    public ParallelKt$mapValuesParallel$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ParallelKt.mapValuesParallel(null, null, this);
    }
}
