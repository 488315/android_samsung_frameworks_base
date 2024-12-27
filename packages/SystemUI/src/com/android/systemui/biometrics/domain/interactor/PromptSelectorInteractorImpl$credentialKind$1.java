package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.PromptKind;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class PromptSelectorInteractorImpl$credentialKind$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ PromptSelectorInteractorImpl this$0;

    public PromptSelectorInteractorImpl$credentialKind$1(PromptSelectorInteractorImpl promptSelectorInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = promptSelectorInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        PromptSelectorInteractorImpl$credentialKind$1 promptSelectorInteractorImpl$credentialKind$1 = new PromptSelectorInteractorImpl$credentialKind$1(this.this$0, (Continuation) obj3);
        promptSelectorInteractorImpl$credentialKind$1.L$0 = (BiometricPromptRequest.Biometric) obj;
        promptSelectorInteractorImpl$credentialKind$1.Z$0 = booleanValue;
        return promptSelectorInteractorImpl$credentialKind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) this.L$0;
        return (biometric == null || !this.Z$0) ? PromptKind.None.INSTANCE : Utils.getCredentialType(this.this$0.lockPatternUtils, biometric.userInfo.deviceCredentialOwnerId);
    }
}
