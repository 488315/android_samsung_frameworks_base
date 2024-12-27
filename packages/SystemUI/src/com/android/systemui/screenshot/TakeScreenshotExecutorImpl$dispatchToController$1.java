package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class TakeScreenshotExecutorImpl$dispatchToController$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ TakeScreenshotExecutorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TakeScreenshotExecutorImpl$dispatchToController$1(TakeScreenshotExecutorImpl takeScreenshotExecutorImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = takeScreenshotExecutorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = this.this$0;
        String str = TakeScreenshotExecutorImpl.TAG;
        return takeScreenshotExecutorImpl.dispatchToController(null, null, null, null, this);
    }
}
