package com.android.systemui.qs.pipeline.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class UserTileSpecRepository$tiles$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserTileSpecRepository this$0;

    public UserTileSpecRepository$tiles$1(UserTileSpecRepository userTileSpecRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = userTileSpecRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.tiles(this);
    }
}
