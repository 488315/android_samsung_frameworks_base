package com.android.systemui.biometrics.domain.interactor;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.PromptKind;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$credentialKind$1", m277f = "PromptSelectorInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptSelectorInteractorImpl$credentialKind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ LockPatternUtils $lockPatternUtils;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptSelectorInteractorImpl$credentialKind$1(LockPatternUtils lockPatternUtils, Continuation<? super PromptSelectorInteractorImpl$credentialKind$1> continuation) {
        super(3, continuation);
        this.$lockPatternUtils = lockPatternUtils;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        PromptSelectorInteractorImpl$credentialKind$1 promptSelectorInteractorImpl$credentialKind$1 = new PromptSelectorInteractorImpl$credentialKind$1(this.$lockPatternUtils, (Continuation) obj3);
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
        boolean z = this.Z$0;
        if (biometric == null || !z) {
            return new PromptKind.Biometric(null, 1, null);
        }
        int credentialType = Utils.getCredentialType(this.$lockPatternUtils, biometric.userInfo.deviceCredentialOwnerId);
        return credentialType != 1 ? credentialType != 2 ? credentialType != 3 ? new PromptKind.Biometric(null, 1, null) : PromptKind.Password.INSTANCE : PromptKind.Pattern.INSTANCE : PromptKind.Pin.INSTANCE;
    }
}
