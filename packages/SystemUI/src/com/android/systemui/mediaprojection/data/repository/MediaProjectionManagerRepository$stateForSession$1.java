package com.android.systemui.mediaprojection.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MediaProjectionManagerRepository$stateForSession$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
