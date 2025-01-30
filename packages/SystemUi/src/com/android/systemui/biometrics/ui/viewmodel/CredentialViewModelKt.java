package com.android.systemui.biometrics.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CredentialViewModelKt {
    public static final String asBadCredentialErrorMessage(Context context, ClassReference classReference) {
        return context.getString(Intrinsics.areEqual(classReference, Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Pin.class)) ? R.string.biometric_dialog_wrong_pin : (!Intrinsics.areEqual(classReference, Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Password.class)) && Intrinsics.areEqual(classReference, Reflection.getOrCreateKotlinClass(BiometricPromptRequest.Credential.Pattern.class))) ? R.string.biometric_dialog_wrong_pattern : R.string.biometric_dialog_wrong_password);
    }
}
