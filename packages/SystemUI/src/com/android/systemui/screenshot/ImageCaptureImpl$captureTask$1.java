package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ImageCaptureImpl$captureTask$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ImageCaptureImpl this$0;

    public ImageCaptureImpl$captureTask$1(ImageCaptureImpl imageCaptureImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = imageCaptureImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ImageCaptureImpl.captureTask$suspendImpl(this.this$0, 0, this);
    }
}
