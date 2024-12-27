package com.android.systemui.biometrics.ui.viewmodel;

import android.content.Context;
import android.hardware.biometrics.PromptContentView;
import android.os.UserManager;
import com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CredentialViewModel$header$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CredentialViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialViewModel$header$1(CredentialViewModel credentialViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = credentialViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        CredentialViewModel$header$1 credentialViewModel$header$1 = new CredentialViewModel$header$1(this.this$0, (Continuation) obj3);
        credentialViewModel$header$1.L$0 = (BiometricPromptRequest.Credential) obj;
        credentialViewModel$header$1.Z$0 = booleanValue;
        return credentialViewModel$header$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BiometricPromptRequest.Credential credential = (BiometricPromptRequest.Credential) this.L$0;
        boolean z2 = this.Z$0;
        if (Flags.customBiometricPrompt()) {
            com.android.systemui.Flags.constraintBp();
            z = true;
        } else {
            z = false;
        }
        boolean z3 = z2 && z;
        BiometricUserInfo biometricUserInfo = credential.userInfo;
        String str = z3 ? "" : credential.subtitle;
        PromptContentView promptContentView = (!z || z3) ? null : credential.contentView;
        String str2 = (!z || credential.contentView == null) ? credential.description : "";
        Context context = this.this$0.applicationContext;
        int i = biometricUserInfo.deviceCredentialOwnerId;
        int i2 = Utils.$r8$clinit;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        return new BiometricPromptHeaderViewModelImpl(credential, biometricUserInfo, credential.title, str, str2, promptContentView, context.getResources().getDrawable(userManager != null ? userManager.isManagedProfile(i) : false ? R.drawable.auth_dialog_enterprise : R.drawable.auth_dialog_lock, context.getTheme()), credential.showEmergencyCallButton);
    }
}
