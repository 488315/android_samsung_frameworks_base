package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class DreamCoordinator$attachFilterOnDreamingStateChange$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DreamCoordinator this$0;

    public DreamCoordinator$attachFilterOnDreamingStateChange$1(DreamCoordinator dreamCoordinator, Continuation continuation) {
        super(continuation);
        this.this$0 = dreamCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object attachFilterOnDreamingStateChange;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        attachFilterOnDreamingStateChange = this.this$0.attachFilterOnDreamingStateChange(this);
        return attachFilterOnDreamingStateChange;
    }
}
