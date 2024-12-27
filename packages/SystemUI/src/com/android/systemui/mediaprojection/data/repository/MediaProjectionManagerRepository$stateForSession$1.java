package com.android.systemui.mediaprojection.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class MediaProjectionManagerRepository$stateForSession$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    public MediaProjectionManagerRepository$stateForSession$1(MediaProjectionManagerRepository mediaProjectionManagerRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaProjectionManagerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MediaProjectionManagerRepository.access$stateForSession(this.this$0, null, null, this);
    }
}
