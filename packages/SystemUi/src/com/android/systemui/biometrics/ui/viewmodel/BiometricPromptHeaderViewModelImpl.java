package com.android.systemui.biometrics.ui.viewmodel;

import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricPromptHeaderViewModelImpl implements CredentialHeaderViewModel {
    public final String description;
    public final Drawable icon;
    public final BiometricPromptRequest.Credential request;
    public final String subtitle;
    public final String title;
    public final UserHandle user;

    public BiometricPromptHeaderViewModelImpl(BiometricPromptRequest.Credential credential, UserHandle userHandle, String str, String str2, String str3, Drawable drawable) {
        this.request = credential;
        this.user = userHandle;
        this.title = str;
        this.subtitle = str2;
        this.description = str3;
        this.icon = drawable;
    }
}
