package com.android.systemui.screenshot.message;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ProfileMessageController$onScreenshotTaken$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ProfileMessageController this$0;

    public ProfileMessageController$onScreenshotTaken$1(ProfileMessageController profileMessageController, Continuation continuation) {
        super(continuation);
        this.this$0 = profileMessageController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.onScreenshotTaken(null, this);
    }
}
