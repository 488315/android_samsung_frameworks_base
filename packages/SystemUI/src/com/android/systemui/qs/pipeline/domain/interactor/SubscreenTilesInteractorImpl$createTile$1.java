package com.android.systemui.qs.pipeline.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class SubscreenTilesInteractorImpl$createTile$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SubscreenTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubscreenTilesInteractorImpl$createTile$1(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = subscreenTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return SubscreenTilesInteractorImpl.access$createTile(this.this$0, null, this);
    }
}
