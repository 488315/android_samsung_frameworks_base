package com.android.systemui.qs.tiles.impl.night.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class NightDisplayTileUserActionInteractor$handleInput$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NightDisplayTileUserActionInteractor this$0;

    public NightDisplayTileUserActionInteractor$handleInput$1(NightDisplayTileUserActionInteractor nightDisplayTileUserActionInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = nightDisplayTileUserActionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.handleInput(null, this);
    }
}
