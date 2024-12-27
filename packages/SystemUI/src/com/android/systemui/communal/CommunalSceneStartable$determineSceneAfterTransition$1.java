package com.android.systemui.communal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class CommunalSceneStartable$determineSceneAfterTransition$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CommunalSceneStartable this$0;

    public CommunalSceneStartable$determineSceneAfterTransition$1(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
        super(continuation);
        this.this$0 = communalSceneStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return CommunalSceneStartable.access$determineSceneAfterTransition(this.this$0, null, this);
    }
}
