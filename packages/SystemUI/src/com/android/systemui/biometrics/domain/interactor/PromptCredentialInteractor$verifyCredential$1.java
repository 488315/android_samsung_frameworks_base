package com.android.systemui.biometrics.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class PromptCredentialInteractor$verifyCredential$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PromptCredentialInteractor this$0;

    public PromptCredentialInteractor$verifyCredential$1(PromptCredentialInteractor promptCredentialInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = promptCredentialInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return PromptCredentialInteractor.access$verifyCredential(this.this$0, null, null, this);
    }
}
