package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class CustomTileUserActionInteractor$longClick$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CustomTileUserActionInteractor this$0;

    public CustomTileUserActionInteractor$longClick$1(CustomTileUserActionInteractor customTileUserActionInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = customTileUserActionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.longClick(null, null, null, 0, this);
    }
}
