package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ScreenshotPolicyImpl$findPrimaryContent$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$findPrimaryContent$1(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = screenshotPolicyImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ScreenshotPolicyImpl.findPrimaryContent$suspendImpl(this.this$0, 0, this);
    }
}
