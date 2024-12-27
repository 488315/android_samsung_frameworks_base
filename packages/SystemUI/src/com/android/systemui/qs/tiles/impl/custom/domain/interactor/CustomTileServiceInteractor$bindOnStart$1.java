package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class CustomTileServiceInteractor$bindOnStart$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CustomTileServiceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileServiceInteractor$bindOnStart$1(CustomTileServiceInteractor customTileServiceInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = customTileServiceInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.bindOnStart(this);
    }
}
