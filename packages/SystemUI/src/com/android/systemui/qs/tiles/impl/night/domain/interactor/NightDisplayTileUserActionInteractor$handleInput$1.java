package com.android.systemui.qs.tiles.impl.night.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NightDisplayTileUserActionInteractor$handleInput$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NightDisplayTileUserActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
