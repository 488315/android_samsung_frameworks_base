package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class TakeScreenshotExecutorImpl$getDisplaysToScreenshot$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ TakeScreenshotExecutorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
