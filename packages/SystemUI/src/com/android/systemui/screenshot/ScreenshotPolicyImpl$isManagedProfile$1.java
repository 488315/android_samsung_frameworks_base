package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ScreenshotPolicyImpl$isManagedProfile$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ScreenshotPolicyImpl this$0;

    public ScreenshotPolicyImpl$isManagedProfile$1(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = screenshotPolicyImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ScreenshotPolicyImpl.isManagedProfile$suspendImpl(this.this$0, 0, this);
    }
}
