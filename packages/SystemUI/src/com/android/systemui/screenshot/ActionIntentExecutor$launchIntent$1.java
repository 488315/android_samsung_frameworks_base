package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class ActionIntentExecutor$launchIntent$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActionIntentExecutor this$0;

    public ActionIntentExecutor$launchIntent$1(ActionIntentExecutor actionIntentExecutor, Continuation continuation) {
        super(continuation);
        this.this$0 = actionIntentExecutor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.launchIntent(null, null, false, null, null, this);
    }
}
