package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ TakeScreenshotExecutorImpl this$0;

    public TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1(TakeScreenshotExecutorImpl takeScreenshotExecutorImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = takeScreenshotExecutorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = this.this$0;
        String str = TakeScreenshotExecutorImpl.TAG;
        return takeScreenshotExecutorImpl.getDisplaysToScreenshot(0, this);
    }
}
