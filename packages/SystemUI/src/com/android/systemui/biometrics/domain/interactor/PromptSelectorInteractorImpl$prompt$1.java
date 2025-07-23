package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.PromptInfo;
import com.android.systemui.biometrics.domain.model.BiometricOperationInfo;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import com.android.systemui.biometrics.shared.model.PromptKind;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PromptSelectorInteractorImpl$prompt$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    /* synthetic */ Object L$4;
    int label;
    final /* synthetic */ PromptSelectorInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptSelectorInteractorImpl$prompt$1(PromptSelectorInteractorImpl promptSelectorInteractorImpl, Continuation continuation) {
        super(6, continuation);
        this.this$0 = promptSelectorInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        PromptSelectorInteractorImpl$prompt$1 promptSelectorInteractorImpl$prompt$1 = new PromptSelectorInteractorImpl$prompt$1(this.this$0, (Continuation) obj6);
        promptSelectorInteractorImpl$prompt$1.L$0 = (PromptInfo) obj;
        promptSelectorInteractorImpl$prompt$1.L$1 = (Long) obj2;
        promptSelectorInteractorImpl$prompt$1.L$2 = (Integer) obj3;
        promptSelectorInteractorImpl$prompt$1.L$3 = (PromptKind) obj4;
        promptSelectorInteractorImpl$prompt$1.L$4 = (String) obj5;
        return promptSelectorInteractorImpl$prompt$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromptInfo promptInfo = (PromptInfo) this.L$0;
        Long l = (Long) this.L$1;
        Integer num = (Integer) this.L$2;
        PromptKind promptKind = (PromptKind) this.L$3;
        String str = (String) this.L$4;
        if (promptInfo == null || num == null || l == null || str == null || !(promptKind instanceof PromptKind.Biometric)) {
            return null;
        }
        int intValue = num.intValue();
        CredentialInteractor credentialInteractor = this.this$0.credentialInteractor;
        return new BiometricPromptRequest.Biometric(promptInfo, new BiometricUserInfo(intValue, ((CredentialInteractorImpl) credentialInteractor).userManager.getCredentialOwnerProfile(num.intValue()), 0, 4, null), new BiometricOperationInfo(l.longValue()), ((PromptKind.Biometric) promptKind).activeModalities, str);
    }
}
