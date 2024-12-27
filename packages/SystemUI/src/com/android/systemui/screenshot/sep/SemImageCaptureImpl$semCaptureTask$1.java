package com.android.systemui.screenshot.sep;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class SemImageCaptureImpl$semCaptureTask$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SemImageCaptureImpl this$0;

    public SemImageCaptureImpl$semCaptureTask$1(SemImageCaptureImpl semImageCaptureImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = semImageCaptureImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.semCaptureTask(0, this);
    }
}
