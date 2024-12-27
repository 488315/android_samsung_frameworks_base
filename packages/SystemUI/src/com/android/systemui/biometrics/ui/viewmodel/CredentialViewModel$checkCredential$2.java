package com.android.systemui.biometrics.ui.viewmodel;

import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class CredentialViewModel$checkCredential$2 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CredentialViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialViewModel$checkCredential$2(CredentialViewModel credentialViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = credentialViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.checkCredential((List) null, (CredentialHeaderViewModel) null, this);
    }
}
