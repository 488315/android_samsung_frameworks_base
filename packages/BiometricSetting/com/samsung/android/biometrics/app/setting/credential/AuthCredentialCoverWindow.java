package com.samsung.android.biometrics.app.setting.credential;

import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public final class AuthCredentialCoverWindow extends AuthCredentialWindow {
    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialWindow
    protected final int getPasswordLayoutId() {
        return R.layout.biometric_prompt_credential_password_cover;
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialWindow
    protected final int getPatternLayoutId() {
        return R.layout.biometric_prompt_credential_pattern_cover;
    }
}
