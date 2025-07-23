package com.android.systemui.qs.pipeline.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SubscreenUserTileSpecRepository$tiles$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SubscreenUserTileSpecRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubscreenUserTileSpecRepository$tiles$1(SubscreenUserTileSpecRepository subscreenUserTileSpecRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = subscreenUserTileSpecRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.tiles(this);
    }
}
