package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.CredentialStatus;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$errorMessage$1", m277f = "CredentialViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class CredentialViewModel$errorMessage$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CredentialViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialViewModel$errorMessage$1(CredentialViewModel credentialViewModel, Continuation<? super CredentialViewModel$errorMessage$1> continuation) {
        super(3, continuation);
        this.this$0 = credentialViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CredentialViewModel$errorMessage$1 credentialViewModel$errorMessage$1 = new CredentialViewModel$errorMessage$1(this.this$0, (Continuation) obj3);
        credentialViewModel$errorMessage$1.L$0 = (CredentialStatus.Fail) obj;
        credentialViewModel$errorMessage$1.L$1 = (BiometricPromptRequest.Credential) obj2;
        return credentialViewModel$errorMessage$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CredentialStatus.Fail fail = (CredentialStatus.Fail) this.L$0;
        BiometricPromptRequest.Credential credential = (BiometricPromptRequest.Credential) this.L$1;
        if (fail instanceof CredentialStatus.Fail.Error) {
            String str = ((CredentialStatus.Fail.Error) fail).error;
            if (str == null) {
                return CredentialViewModelKt.asBadCredentialErrorMessage(this.this$0.applicationContext, Reflection.getOrCreateKotlinClass(credential != null ? credential.getClass() : BiometricPromptRequest.Credential.Password.class));
            }
            return str;
        }
        if (fail instanceof CredentialStatus.Fail.Throttled) {
            return ((CredentialStatus.Fail.Throttled) fail).error;
        }
        if (fail == null) {
            return "";
        }
        throw new NoWhenBranchMatchedException();
    }
}
