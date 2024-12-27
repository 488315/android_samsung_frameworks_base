package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class PromptViewModel$needsExplicitConfirmation$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PromptViewModel this$0;

    public PromptViewModel$needsExplicitConfirmation$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        PromptViewModel promptViewModel = this.this$0;
        int i = PromptViewModel.$r8$clinit;
        return promptViewModel.needsExplicitConfirmation(null, this);
    }
}
