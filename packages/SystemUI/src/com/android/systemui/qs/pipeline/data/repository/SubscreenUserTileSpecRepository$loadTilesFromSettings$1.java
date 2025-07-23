package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.repository.SubscreenUserTileSpecRepository;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SubscreenUserTileSpecRepository$loadTilesFromSettings$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SubscreenUserTileSpecRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubscreenUserTileSpecRepository$loadTilesFromSettings$1(SubscreenUserTileSpecRepository subscreenUserTileSpecRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = subscreenUserTileSpecRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        SubscreenUserTileSpecRepository subscreenUserTileSpecRepository = this.this$0;
        SubscreenUserTileSpecRepository.Companion companion = SubscreenUserTileSpecRepository.Companion;
        return subscreenUserTileSpecRepository.loadTilesFromSettings(0, this);
    }
}
