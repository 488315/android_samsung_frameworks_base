package com.android.systemui.biometrics.ui.viewmodel;

import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptContentView;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BiometricPromptHeaderViewModelImpl implements CredentialHeaderViewModel {
    public final PromptContentView contentView;
    public final String description;
    public final Drawable icon;
    public final BiometricPromptRequest.Credential request;
    public final boolean showEmergencyCallButton;
    public final String subtitle;
    public final String title;
    public final BiometricUserInfo user;

    public BiometricPromptHeaderViewModelImpl(BiometricPromptRequest.Credential credential, BiometricUserInfo biometricUserInfo, String str, String str2, String str3, PromptContentView promptContentView, Drawable drawable, boolean z) {
        this.request = credential;
        this.user = biometricUserInfo;
        this.title = str;
        this.subtitle = str2;
        this.description = str3;
        this.contentView = promptContentView;
        this.icon = drawable;
        this.showEmergencyCallButton = z;
    }
}
