package com.android.systemui.biometrics.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PromptCredentialInteractor$verifyCredential$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PromptCredentialInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
