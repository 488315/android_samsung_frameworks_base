package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.PromptInfo;
import com.android.systemui.biometrics.domain.model.BiometricOperationInfo;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.domain.model.BiometricUserInfo;
import com.android.systemui.biometrics.shared.model.PromptKind;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$prompt$1", m277f = "PromptCredentialInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptCredentialInteractor$prompt$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;
    final /* synthetic */ PromptCredentialInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptCredentialInteractor$prompt$1(PromptCredentialInteractor promptCredentialInteractor, Continuation<? super PromptCredentialInteractor$prompt$1> continuation) {
        super(5, continuation);
        this.this$0 = promptCredentialInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        PromptCredentialInteractor$prompt$1 promptCredentialInteractor$prompt$1 = new PromptCredentialInteractor$prompt$1(this.this$0, (Continuation) obj5);
        promptCredentialInteractor$prompt$1.L$0 = (PromptInfo) obj;
        promptCredentialInteractor$prompt$1.L$1 = (Long) obj2;
        promptCredentialInteractor$prompt$1.L$2 = (Integer) obj3;
        promptCredentialInteractor$prompt$1.L$3 = (PromptKind) obj4;
        return promptCredentialInteractor$prompt$1.invokeSuspend(Unit.INSTANCE);
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
        if (promptInfo != null && num != null && l != null) {
            if (Intrinsics.areEqual(promptKind, PromptKind.Pin.INSTANCE)) {
                PromptCredentialInteractor promptCredentialInteractor = this.this$0;
                int intValue = num.intValue();
                BiometricUserInfo biometricUserInfo = new BiometricUserInfo(intValue, ((CredentialInteractorImpl) promptCredentialInteractor.credentialInteractor).userManager.getCredentialOwnerProfile(intValue));
                PromptCredentialInteractor promptCredentialInteractor2 = this.this$0;
                long longValue = l.longValue();
                promptCredentialInteractor2.getClass();
                return new BiometricPromptRequest.Credential.Pin(promptInfo, biometricUserInfo, new BiometricOperationInfo(longValue));
            }
            if (Intrinsics.areEqual(promptKind, PromptKind.Pattern.INSTANCE)) {
                PromptCredentialInteractor promptCredentialInteractor3 = this.this$0;
                int intValue2 = num.intValue();
                BiometricUserInfo biometricUserInfo2 = new BiometricUserInfo(intValue2, ((CredentialInteractorImpl) promptCredentialInteractor3.credentialInteractor).userManager.getCredentialOwnerProfile(intValue2));
                PromptCredentialInteractor promptCredentialInteractor4 = this.this$0;
                long longValue2 = l.longValue();
                promptCredentialInteractor4.getClass();
                BiometricOperationInfo biometricOperationInfo = new BiometricOperationInfo(longValue2);
                CredentialInteractor credentialInteractor = this.this$0.credentialInteractor;
                return new BiometricPromptRequest.Credential.Pattern(promptInfo, biometricUserInfo2, biometricOperationInfo, !((CredentialInteractorImpl) credentialInteractor).lockPatternUtils.isVisiblePatternEnabled(num.intValue()));
            }
            if (Intrinsics.areEqual(promptKind, PromptKind.Password.INSTANCE)) {
                PromptCredentialInteractor promptCredentialInteractor5 = this.this$0;
                int intValue3 = num.intValue();
                BiometricUserInfo biometricUserInfo3 = new BiometricUserInfo(intValue3, ((CredentialInteractorImpl) promptCredentialInteractor5.credentialInteractor).userManager.getCredentialOwnerProfile(intValue3));
                PromptCredentialInteractor promptCredentialInteractor6 = this.this$0;
                long longValue3 = l.longValue();
                promptCredentialInteractor6.getClass();
                return new BiometricPromptRequest.Credential.Password(promptInfo, biometricUserInfo3, new BiometricOperationInfo(longValue3));
            }
        }
        return null;
    }
}
