package com.android.systemui.biometrics.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CredentialViewModelKt {
    public static final String asBadCredentialErrorMessage(Context context, ClassReference classReference) {
        return context.getString(classReference.equals(Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Pin.class)) ? R.string.biometric_dialog_wrong_pin : (!classReference.equals(Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Password.class)) && classReference.equals(Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Pattern.class))) ? R.string.biometric_dialog_wrong_pattern : R.string.biometric_dialog_wrong_password);
    }
}
