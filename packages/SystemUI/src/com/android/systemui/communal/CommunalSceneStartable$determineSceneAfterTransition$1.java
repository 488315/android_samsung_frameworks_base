package com.android.systemui.communal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class CommunalSceneStartable$determineSceneAfterTransition$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CommunalSceneStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
