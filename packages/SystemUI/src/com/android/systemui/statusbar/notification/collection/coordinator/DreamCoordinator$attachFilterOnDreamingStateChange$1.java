package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DreamCoordinator$attachFilterOnDreamingStateChange$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DreamCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
