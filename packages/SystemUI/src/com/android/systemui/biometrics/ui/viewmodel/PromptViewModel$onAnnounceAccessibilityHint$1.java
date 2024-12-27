package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class PromptViewModel$onAnnounceAccessibilityHint$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PromptViewModel this$0;

    public PromptViewModel$onAnnounceAccessibilityHint$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.onAnnounceAccessibilityHint(null, false, this);
    }
}
