package com.android.systemui.qs.pipeline.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class TileSpecSettingsRepository$tilesSpecs$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ TileSpecSettingsRepository this$0;

    public TileSpecSettingsRepository$tilesSpecs$1(TileSpecSettingsRepository tileSpecSettingsRepository, Continuation continuation) {
        super(continuation);
        this.this$0 = tileSpecSettingsRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.tilesSpecs(0, this);
    }
}
