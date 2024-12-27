package com.android.systemui.biometrics.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class CredentialViewModelKt {
    public static final String asBadCredentialErrorMessage(Context context, ClassReference classReference) {
        return context.getString(classReference.equals(Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Pin.class)) ? R.string.biometric_dialog_wrong_pin : (!classReference.equals(Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Password.class)) && classReference.equals(Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Pattern.class))) ? R.string.biometric_dialog_wrong_pattern : R.string.biometric_dialog_wrong_password);
    }
}
