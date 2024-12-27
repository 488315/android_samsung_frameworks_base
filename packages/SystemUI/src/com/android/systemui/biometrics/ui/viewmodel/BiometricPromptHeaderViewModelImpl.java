package com.android.systemui.biometrics.ui.viewmodel;

import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptContentView;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;

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
