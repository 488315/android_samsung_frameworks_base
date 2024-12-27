package com.android.systemui.qs.panels.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class EditTilesListInteractor$getTilesToEdit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ EditTilesListInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditTilesListInteractor$getTilesToEdit$1(EditTilesListInteractor editTilesListInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = editTilesListInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getTilesToEdit(this);
    }
}
